## ExoPlayer란?

Exoplayer는 구글에서 만든 미디어 재생 라이브러리로, 다양한 종류의 미디어 파일을 쉽게 재생할 수 있도록 도와준다. 심지어 별 다른 설정 없이도 네트워크로부터 미디어를 스트리밍 형태로 불러와 재생할 수도 있고 다양한 포맷들을 지원하며 커스터마이징도 지원한다.

## ExoPlayer에서 지원하는 포맷들

ExoPlayer에서는 다양한 포맷들을 지원하고 있으며, 크게 아래의 포맷들을 지원합니다.

### Adaptive Stremaing

적응형 스트리밍으로, 사용자의 네트워크 상태에 적응해서 스트리밍을 해주는 기술입니다.
동영상 콘텐츠를 다양한 해상도로 인코딩해서 저장하고 이를 잘게 잘게쪼개어 저장을 해놨다가 사용자의 네트워크 상황에 따라서 최적의 동영상 콘텐츠 조각을 가져와 스트리밍 해주는 방식이다.

Exoplayer에 큰 특징은 DASH를 지원한다는 것이다.

### DASH

```kotlin
네트워크 상황에 따라 동영상의 품질을 조절하는 기술.
해당 기술을 통해 사용자는 네트워크 상황에 따라 동영상이 멈추는 것이 아닌
끊기지 않는 동영상 시청이 가능하다.
```

그 외에도 HLS, SmoothStreaming을 지원한다.

## ExoPlayer 주요 컴포넌트

### PlayerView

```kotlin
비디오를 불러와서 실제 UI에 부려줄때 사용되는 UI 요소로 xml에 선언을 해놓고
SimpleExoPlayer와 바인딩하여 사용합니다.
```

### ExoPlayer

```kotlin
비디오를 화면에 뿌려주는 가장 중요한 역할을 하는 컴포넌트입니다.
```

### DataSourceFactory

```kotlin
MediaSource를 생성할 때 DataSourceFactory를 넣어줘야 하는데,
DataSource는 URI 리소스부터 데이터를 읽는데 사용한다.
여러 종류의 DataSource가 있는데 결국은 모두 DataSource를 상속받아 만들어졌다.
```

- DefaultDataSourceFactory : 가장 기본적인 DataSourceFactory
- DefaultHttpDataSoruceFactory : Android의 HttpURLConnection

### MediaItem

```kotlin
MediaItem은 Media를 재생하기 가장 작은 항목으로 Uri를 기반으로 MediaItem을 생성할 수 있다.
또한 미디어에서 다양한 MetaData를 세팅할 수 있다.
대표적인 예로 재생목록같은 경우가 있는데, 실제 재생을 위해서는 MediaItem을 이용하여
MediaSource를 만들어야 한다.
```

### MediaSource

```kotlin
ExoPlayer에서 재생을 하기 위해서는 MediaSource가 필요한데, MediaSource는 MediaItem을 이용해
생성한 뒤 ExoPlayer와 연결하여 사용한다.
```

일반 미디어 파일 형식 재생

```kotlin
val url = "https://www.test.com/test.mp4
val mediaItem = MediaItem.fromUri(Uri.parse(url))

//일반 미디어 파일 형식
val progressiveMediaSource = ProgressiveMediaSource.factory(factory).createMediaSource(mediaItem)
// Dash 형식
val dashMediasource = DashMediaSource.Factory(factory).createMediaSource(mediaItem)

//SmoothStreaming 형식 
val ssmediaSource = SsMediaSource.Factory(factory).createMediaSource(mediaItem)

//Hls 형식 
val hlsMediaSource = HlsMediaSource.Factory(factory).createMediaSource(mediaItem)

//각 MediaSource를 생성할 때 이용하는 factory객체가 틀리다.
//progressive -> DefaultDataSourceFactory
//Dash, SmoothStreaming -> DefaultHttpDataSourceFactory

하지만 최근 버전에서는 DefaultMediaSourceFactory가 아닌 ProgressiveMediaSource를 이용한다.
```

### Track Selector

```kotlin
TrackSelector는 영상의 track 정보를 세팅하는 역할들 하는 컴포넌트로 비디오의 비트레이트,
비디오 사이즈, 대역폭등 다양한 설정을 도와준다.
```

### PlayerControlView

```kotlin
PlayerControlView는 재생을 도와주는 UI 컴포넌트이다.
```

```kotlin
<com.google.android.exoplayer3.ui.PlayerView
    android:id="@+id/player_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
    
    //custome을 하고 싶다면 controll_layout_id에 연결해주면 된다.
    //app:controller_layout_id="@layout/custom_controls"
```

# ExoPlayer vs MediaPlayer

![Untitled](https://miro.medium.com/v2/resize:fit:1276/0*YfcIUrMQH3ySSb3w)

## MediaPlayer

- 기본적으로 휴대폰 로컬에 설치되므로 어떤 버전을 사용할 지 제어할 수 없다.
- 따라서 MediaPlayer 버전, OS, 휴대폰에 따라 다르게 구현되어있을 수 있고, 여러 상태의 버그가 존재하여 디버깅 하기 어렵다.
- 저 전력으로 오디오 디코딩을 할 수 있어 오디오 재생 기능이 좋다.

## ExoPlayer

- Android 프레임워크에 속하지 않고, Android Sdk에서 별도로 배포되는 오픈소스 프로젝트이다.
- Android API 레벨 16부터 사용가능하다.
- 라이브러리이므로 앱을 업데이트하여 새로운 기능을 쉽게 사용할 수 있다.
    - 특히 ExoPlayer는 MediaPlayer에서 지원하지 않는 DASH, SmoothStreaming, HLS등을 지원한다.
- 사용하려는 용도에 맞춰 설정 변경이 가능하며 변경이 편리하다.

단순하게 오디오 재생의 관점에서 보면 MediaPlayer로 구현하는것이 정답이다.

하지만 오디오 재생에서 네트워크 상황에 따라 동영상의 해상도를 조절해서, 끊김없이 영상을 제공하는 서비스와

스트리밍 서비스를 사용하는 경우와 같이 고급 기능을 사용한다면 ExoPlayer를 사용하는것이 적합하다.

### 참고

https://jungwoon.github.io/android/library/2020/11/06/ExoPlayer.html

https://developer.android.com/media/media3/exoplayer?hl=ko&_gl=1*1wkp4xq*_up*MQ..*_ga*MTQ3ODk4NDk1Ni4xNzEzNjE4OTk1*_ga_6HH9YJMN9M*MTcxMzYxODk5NC4xLjAuMTcxMzYxODk5NC4wLjAuMA..