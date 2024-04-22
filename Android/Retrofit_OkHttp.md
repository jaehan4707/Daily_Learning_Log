# Retrofit, OkHttp

**Retrofit, OkHttp은 Android에서 네트워크 작업을 할 때 사용하는 대표적인 라이브러리이다.**

안드로이드 초창기에는 네트워크 작업을 수행하기 위해서 HttpUrlConnection, Apache HTTP Client를 사용했다.    
하지만 이후 여러 Third-Party 라이브러리인 `OkHttp`, `Volley`, `Retrofit` 등이 생겨났고,   
현재는 Retrofit이 많이 사용되는 추세이고, 저번 프로젝트에서도 네트워크 작업에서 Retrofit을 사용했다.

레트로핏의 장점은 다음과 같다.

1.  직관적인 어노테이션 사용
2.  통신 성공 시 자동으로 String으로 전달되는 Json을 직접 변화해줄 필요가 없다.
3.  네트워크 호출의 결과값이 메인 스레드에 전달된다.

## OkHttp

```
val client: OkHttpClient = OkHttpClient.Builder().run{
    readTimeout(5000, TimeUnit.MILLISECONDS)
    connectTimeout(5000, TimeUnit.MILLISECONDS)
    addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
}.build()
val postRequest  = Request.Builder().url("http:xxx.xxx.xxx").post()
val getRequest  = Request.Builder().url("http:xxx.xxx.xxx").get()
val deleteRequest  = Request.Builder().url("http:xxx.xxx.xxx").delete()
val putRequest  = Request.Builder().url("http:xxx.xxx.xxx").put()
client.newCall(postRequest).enqueue(object :Callback, okhttp3.Callback {
    override fun onFailure(call: okhttp3.Call, e: IOException) {
        //실패 처리
    }

    override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
		    //response의 body를 적절한 객체로 변환해야 한다.
        //성공 처리
        runOnUiThread{ //backGroundThread에서 수행되기 때문에 UI 관련 작업을 위해 Thread를 변경
        
        }
    }
})
```

## Retrofit

```
//1
private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()
        
//2
private val client: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(5000, TimeUnit.MILLISECONDS)
        .connectTimeout(5000, TimeUnit.MILLISECONDS)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
//3
private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
```

retrofit은 gsonConverter를 통해서 응답으로 받은 Json객체를 Object로 변환해 준다. 하지만 네트워크의 응답으로 적절하지 못한 Json 객체가 들어올 경우 오류가 발생하는데, Json 파싱의 규칙에 적합하지 않은 Json 객체가 들어오기 때문이다. 이를 방지하기 위해 Json 파싱의 규칙을 유연하게 하는 작업이다.

OkHttpClient 인스턴스를 생성한다. OkHttpClient는 타임아웃 설정, 네트워크 통신에 대한 Intercept 처리가 가능하다.

Retrofit 생성

공통적으로 사용될 baseUrl을 지정할 수 있다.

clinet는 OkHttpClient를 지정해준다.

다음으로 Json 객체를 Object로 변환해 주는 ConverterFactory를 추가해 준다.

retrofit은 api query를 service interface를 이용하여 작성한다.

```
interface StoreApi {

    @GET("{storeId}")
    suspend fun getStoreInfo(
        @Path("storeId") storeId: String
    ): Response<StoreDTO>
}
```

Retrofit의 장점 중 하나인 직관적인 어노테이션을 볼 수 있는 부분이다.

OkHttp와 비교하면 정말 직관적이고 , 손쉽게 GET 요청을 할 수 있다.

흔히들 api를 호출하기 위해서 다음과 같은 코드를 작성한다.

```
val storeApi = retrofit.create(StoreApi::class.java)
```

하지만 retrofit의 create는

```
inline fun <reified T> Retrofit.create(): T = create(T::class.java)
```

타입을 명시해 줄 경우 create 안에 클래스를 자동으로 만들어주기 때문에 명시하지 않아도 되기 때문에 다음과 같이 사용할 수 있다.

```
val storeApi : StoreApi = retrofit.create()
```

위 코드를 사용하면 안 되는 것은 아니지만, 아래 코드가 훨씬 직관적이고 깔끔해 보인다.

Retrofit을 통해 네트워크 요청을 처리하는 방법은 여러 가지가 있다.

### Call

enqueue

```
 @GET("{storeId}")
    fun getStoreInfo(
        @Path("storeId") storeId:String
    ) : Call<StoreDTO>
```

```
void enqueue(Callback<T> callback);

val storeApi : StoreApi = Application.retrofit.create()
        val call = storeApi.getStoreInfo(STORE_ID).enqueue(object : Callback<StoreDTO>{
            override fun onResponse(call: Call<StoreDTO>, response: Response<StoreDTO>) {
                if(response.isSuccessful){
                    //성공 처리
                } else{
                    //요청 실패
                }
            }
            override fun onFailure(call: Call<StoreDTO>, t: Throwable) {
                //네트워크 통신 오류
            }
        }
```

enqueue는 서버에 비동기적 요청을 하고 응답을 반환한다.

비동기적 요청이기 때문에 UI Thread를 Block 하지 않는다.

그리고 execute에서 네트워크 요청 실패 시 발생하던 IOException 대신 Callback을 통해 네트워크 연결의 성공, 실패에 대한 다양한 처리가 가능하다.

execute

```
Response<T> execute() throws IOException;

  val response =storeApi.getStoreInfo(STORE_ID).execute()
        if(response.isSuccessful){
            //성공 처리
        } else {
            //요청 실패
        }
```

execute의 반환타입은 Response <T>이다.

하지만 execute는 enqueue와 다르게 동기호출이다.

사실 네트워크 작업을 할 경우 동기호출을 사용하는 상황이 있겠지만은,, 아무튼

싱글스레드로 동작하기 때문에 요청을 보내고 응답을 받을 때까지 스레드가 Block 된다.

따라서 사용을 할 경우 별도의 스레드나 코루틴 스코프 내에서 호출해야 한다.

그렇지 않을 경우 NetworkOnMainThreadException이 발생한다.

```
 lifecycleScope.launch {
            val response = withContext(Dispatchers.IO) {
                storeApi.getStoreInfo(STORE_ID).execute()
            }
            if (response.isSuccessful) {
                //성공 처리
            } else {
                //요청 실패
            }
        }
```

간단한 작성을 통해 Activity에서 호출했지만, 실제는 viewModel에서 호출하기 때문에 lifecycleScope가 아닌 viewModelScope로 감싼다.

Call을 사용한 Retrofit의 코드를 봤는데, execute 방식은 거의 사용할 일이 없고, 비동기적 호출인 enqueue를 주로 사용한다. 하지만 enqueue의 방식도 코드의 길이가 굉장히 길고, 복잡하다.

### Coroutine + Response

코루틴 스코프에서 호출하기 위해서 Api의 함수들을 suspend로 변경해야 한다.

```
@GET("{storeId}")
    suspend fun getStoreInfo(
        @Path("storeId") storeId: String
    ): Response<StoreDTO>
```

```
lifecycleScope.launch { 
            val response = storeApi.getStoreInfo(STORE_ID)
            if(response.isSuccessful){
                //성공 처리
            } else {
                //실패 처리
            }
        }
```

Call의 enqueue와 다르게 성공과 실패에 대한 핸들링을 따로 해줘야 한다.

```
 lifecycleScope.launch {
            try {
                val response = storeApi.getStoreInfo(STORE_ID)
                if (response.isSuccessful) {
                    // 성공 처리
                } else {
                    // 실패 처리
                }
            } catch (e: Exception) {
                Log.e(TAG,e.message.toString())
            }
        }
```

다음 방식은 Reponse를 Result 클래스로 매핑하는 방법이다.

Result 클래스는 RunCatching을 사용할 수 있는 장점이 있다.

```
public inline fun <T, R> T.runCatching(block: T.() -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: Throwable) {
        Result.failure(e)
    }
}
```

runCatching은 위 response에서 직접 작성했던 `try-catch`문이 작성되어 있다.

```
lifecycleScope.launch {
      storeApi.getStoreInfo(STORE_ID).onSuccess { dto ->
		       //성공 처리
      }.onFailure { e ->
		      //실패 처리
      }
  }
```

개인적으론 `try -catch` 보다 result를 이용한 `onSuccess`와 `onFailure`을 작성하는 코드가 훨씬 이쁘고 가독성이 좋은 것 같다.

그 외에도 result는 다음과 같은 메서드가 있다.

-   getOrNull : result를 반환하거나, 없는 경우 null을 반환
-   getOrThrow : result를 반환하거나, 값이 없는 경우 예외를 던진다.
-   getOrElse : result를 반환하거나, 없는 경우 대체 값을 반환한다.