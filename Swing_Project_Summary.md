# 스윙 트레이딩 시스템 전체 정리

## 📋 문서 목록 및 목적

| 문서 | 목적 |
|-----|------|
| Dev_Report_2026_01_17.md | Tracker 3.0 + DB 분리 작업 상세 리포트 |
| Dev_Report_2026_01_18.md | 보라색 스타일링, Document ID, Firebase 최적화 |
| Swing_Migration_Request.md | swing_signals 분리 요구사항 정의 |
| Swing_Report_Logic.md | 리포트 렌더링 아키텍처 설명 |
| Swing_Signal_Migration_Plan.md | DB 마이그레이션 구현 계획 |
| Swing_System_Overhaul_Master_Plan.md | 전체 시스템 개편 마스터 플랜 |
| Swing_Tracker_V3_Implementation_Plan.md | 트래커 3.0 구현 상세 명세 |
| Swing_Trading_Data_Map.md | UI 컴포넌트별 데이터 소스 매핑 |

---

## ✅ 완료된 작업 (Completed)

### 1. 데이터베이스 분리
- [x] `swing_signals` 컬렉션 생성
- [x] 기존 `live_signals`에서 스윙 데이터 마이그레이션
- [x] Document ID 형식: `{티커}-{전략번호}` (예: `NVDA-1`)
- [x] `setDoc` 사용으로 결정적 ID 생성

### 2. 읽기/쓰기 로직 분리
- [x] `submitSwingSignal`: `swing_signals`에 저장
- [x] `loadSwingHistory`: `swing_signals`에서 조회
- [x] `updateStrategyStatus`: 컬렉션 자동 분기
- [x] `deleteStrategy`: 컬렉션 자동 분기

### 3. Bot.js 격리
- [x] Firestore 리스너에서 `type === 'swing'` 스킵
- [x] `loadBackup()`에서 스윙 전략 필터링
- [x] `logDetailedStrategyList()` 스윙 코드 제거

### 4. UI 스타일링
- [x] 사이드바 카드 수정 감지 (`modificationHistory`)
- [x] 보라색 테두리/텍스트 색상 우선순위 정립
- [x] Fallback 로직 수정 (모든 필드 보라색 → 수정된 필드만)

### 5. Firebase 최적화
- [x] 불필요한 `.filter(s => s.type === 'swing')` 제거 (14곳)
- [x] `globalSwingUnsubscribe` Active 상태만 구독
- [x] Northbridge `live_signals` → `swing_signals` 수정

### 6. 타임 머신 로직
- [x] `getSwingStrategySnapshot` 구현
- [x] 과거 날짜 조회 시 해당 시점 상태 재현

---

## ⏳ 미완료 / 향후 작업 (Pending)

### 1. Tracker 3.0 고도화 (일부)
- [ ] `swing-tracker.js` 독립 모듈화
- [ ] 단거리 도장깨기 로직 완전 분리
- [ ] BREAK 전략 일봉 선행 검사 자동화

### 2. 추가 최적화 (보류)
- [ ] `loadContentForDate` Day 전략 중복 조회 수정 (리스크)
- [ ] 오늘 날짜 리스너 중복 제거
- [ ] removed 이벤트 debounce

### 3. 관리자 기능
- [ ] Force Re-track 버튼 UI 추가 (카드 내)
- [ ] 일괄 Re-track 기능

---

## 📊 데이터 구조

### swing_signals 컬렉션
```javascript
{
    // 불변 필드
    ticker: "AAPL",
    action: "BUY AT",
    price: 150.00,           // 진입가
    target: 165.00,          // 목표가
    stopLoss: 145.00,        // 손절가
    createdAt: Timestamp,
    type: "swing",
    date: "2026-01-10",
    strategyNumber: 1,
    
    // 트래커 관리 필드
    status: "Pending",       // Pending | Filled | Success | Stopped | Canceled
    filledAt: Timestamp,
    filledPrice: 150.00,
    exitAt: Timestamp,
    exitPrice: 165.00,
    lastChecked: "2026-01-15",
    
    // 수정 추적
    isUpdated: true,
    modificationHistory: [{ before: {...}, after: {...}, modifiedAt: Timestamp }]
}
```

### UI 컴포넌트 → 데이터 매핑
| UI 컴포넌트 | 컬렉션 | 필터 |
|-----------|--------|------|
| 전략 카드 | `swing_signals` | - |
| 차트 마커 | `swing_signals` | - |
| 대시보드 통계 | `swing_signals` | - |
| 데일리 브리핑 | `insights` | `category == 'swing'` |
| 달력 점(Dot) | `insights` | `category == 'swing'` |
| 월 수익률 | `swing_signals` | `status in [Success, Stopped]` |

---

## 🔐 Firebase Rules

```javascript
// swing_signals 규칙 (적용됨)
match /swing_signals/{document=**} {
  allow read: if isAuthenticated();
  allow write: if isAdmin();
}
```

---

## 🎨 색상 우선순위

### 카드 테두리
| 우선순위 | 상태 | 색상 |
|---------|------|------|
| 1 | Canceled | ⚪ 회색 |
| 2 | Updated | 🟣 보라색 |
| 3 | Success | 🟢 초록색 |
| 4 | Stopped | 🔴 빨간색 |
| 5 | Filled | 🔵 파란색 |
| 6 | Pending | 🟡 노란색 |

### 필드 텍스트
- `modificationHistory`에서 변경된 필드만 🟣 보라색
- 수정 후 Success/Stopped 되어도 보라색 유지

---

## 🔧 핵심 트래킹 로직

### 동시 도달 판정 (Candle-Color Rule)
| 캔들 | 판정 |
|-----|------|
| 🟢 양봉 (Close > Open) | 저점 먼저 → **익절(Success)** |
| 🔴 음봉 (Close < Open) | 고점 먼저 → **손절(Stopped)** |
| ⚪ 도지 (Close == Open) | 보수적 → **손절(Stopped)** |

### 갭(Gap) 처리
- Gap Up (시가 > 목표가): 시가에 익절 체결
- Gap Down (시가 < 손절가): 시가에 손절 체결

### Ghost Trade 방지
- 전략 생성 시점(`createdAt`) 이전 데이터 무시
- 분(Minute) 단위 정밀 비교
