# VideoAlarm
### 동영상을 처음부터 끝까지 재생하는 알람 앱
---

## 1. 프로젝트 소개
스마트폰 기본 알람은 15 초 이하의 짧은 영상 / 단순 음원만 지원합니다. **VideoAlarm**은 '기기에 저장된' 전체 영상을 재생해 사용자에게 ‘보고 듣는’ 알람을 제공합니다.   

## 2. 주요 기능
### 1️⃣ 영상 알람 설정
- 기기 내 폴더에서 동영상 선택 → 알람에 등록  
- 알람마다 별도 요일 지정.

### 2️⃣ 전체 화면 & 잠금 화면 재생
- **잠금화면 위**에서 풀스크린 재생. 
- 가로/세로 자동 전환, 재생 위치 유지.
- 단, 휴대폰 화면이 켜져 있는 경우에는 상단 베너로 알림.

### 3️⃣ 간편 해제
- **더블 탭** 또는 **정지 버튼**으로 알람 해제.  

### 4️⃣ 정확한 알람 스케줄 (Exact Alarm)
- Android 12+ → `USE_EXACT_ALARM`, 13 / 14+ → `SCHEDULE_EXACT_ALARM` 권한 흐름을 처리하여 정시에 깨워줍니다.

### 5️⃣ 알람 관리  
- 생성/삭제/편집 시 Room DB와 동기화.

---

## 3. 기술 스택

| 역할 | 종류 |
| --- | --- |
| **Language** | <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white"/> |
| **UI** | <img alt="Jetpack Compose" src="https://img.shields.io/badge/Jetpack Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white"/> |
| **Media** | <img alt="Media3 ExoPlayer" src="https://img.shields.io/badge/Media3 ExoPlayer-FF6F00?style=for-the-badge&logo=google&logoColor=white"/> |
| **DI** | <img alt="Hilt" src="https://img.shields.io/badge/Hilt-009688?style=for-the-badge&logo=dagger&logoColor=white"/> |
| **DB(Local)** | <img alt="Room" src="https://img.shields.io/badge/Room-6D4C41?style=for-the-badge&logo=sqlite&logoColor=white"/> |
| **Core** | <img alt="AlarmManager" src="https://img.shields.io/badge/AlarmManager-3E2723?style=for-the-badge&logo=android&logoColor=white"/> |
| **Build** | <img alt="Gradle" src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"/> |
| **Version Control** | <img alt="Git" src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white"/> <img alt="GitHub" src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white"/> |

---
<!--
## 4. 프로젝트 구조
app/
├─ presentation/ # Jetpack Compose UI
├─ domain/ # UseCase · Model
├─ data/
│ ├─ local/ # Room DAO · Entity · DB
│ └─ repository/
├─ di/ # Hilt Modules
└─ service/ # ForegroundService · AlarmReceiver


> **아키텍처** — MVVM + Clean Architecture 구성으로 도메인 로직과 UI를 분리해 테스트 용이성을 확보했습니다. Foreground Service에서 Media3 ExoPlayer를 실행해, 재생 중 시스템이 앱을 종료하지 않도록 보호합니다.
-->
---

### 🎥 사용 예시 (Screenshots)

| | |
|---|---|
| ![홈 화면](resource/home.jpg) | ![알람 설정 화면](resource/set.jpg) |
| ![동영상 선택 화면](resource/choose.jpg) | ![설정 완료 화면](resource/result.jpg) |

