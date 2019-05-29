프레임워크 - read.me 문서
*NgdroidApp
우리는 가멜라브 이스탄불(http://gamelab.istanbul)과 2개 대학에서 게임 프로그래밍을 가르치고 있습니다. 
이 간단한 자바 프레임워크는 수업시간에 사용하기 위해 만들어졌습니다. 
따라서, 안드로이드 구성으로 시간을 보내는 대신에 학생들은 게임 코드를 가능한 한 빨리 쓰기 시작할 수 있습니다.

프레임워크는 Android Studio 프로젝트 입니다. 포크를 하고 Android Studio를 통해 포크를 컴퓨터에 복제할 수 있습니다.

*이 프레임워크는 다음과 같은 특징을 가집니다.

GameActivity(출발점)
AppManager(표면뷰),
main loop(메인 스레드)
NgApp(루트 클래스)
CanvasManager 와 GameCanvas

당신은 게임캔버스를 열고 게임 코딩을 시작하기만 하면 됩니다.

*프레임워크는 MVC 패턴으로 설계되었습니다.
만약  메뉴 및 다른 뷰를 위한 새 클래스를 만들어야 하는 경우, BaseCanvas를 확장하고 새 하위 캔버스를 현재 캔버스로 설정할 수 있습니다.

*자체 APK를 생성하려는 경우

res/values/strings.xml에서 앱 이름을 변경하십시오.
com.mycompany.myngdroidapp을 자신의 패키지 이름으로 변경하십시오.
게임 아이콘을 res/mipmap 폴더에 넣으십시오.
app/build.graddle 수정 및
AndroidManifest.xml을 수정하십시오.
아직 개발해야 할 것이 많습니다. 기여 해주시면 정말 감사합니다.

*이 프레임워크는 MIT 라이선스로 허가됩니다.

설치 알림
NgdroidApp은 Android Studio 프로젝트로 설계되었습니다. 
따라서 Android Studio를 통해 복제하고 열 수 있습니다. 
일부 사용자들은 안드로이드 스튜디오가 때때로 ngdroidApp 프로젝트를 열 수 없다고 보고했습니다다. 
이런 문제가 발생할 경우 "파일->프로젝트 닫기"를 통해 프로젝트를 닫고 "
기존 Android Studio 프로젝트 열기"를 선택한 다음 프로젝트 탐색기 창을 통해 프로젝트를 찾아 여십시오.