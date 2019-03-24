# Spring Swagger Demo

Kotlin 기반의 Spring Rest API 의 Swagger 데모 입니다.


## 1. Swagger 란?

스프링 API 를 애노테이션 기반으로 문서화하기 위한 툴이다. (너무 간단...?)


## 2. Swagger 기본 설정

#### 2.1. `build.gradle`에 해당 의존성을 추가합니다.

```groovy
ext {
    swaggerVersion = '2.9.2'
}

compile "io.springfox:springfox-swagger2:${swaggerVersion}"
compile "io.springfox:springfox-swagger-ui:${swaggerVersion}"
```

#### 2.2. `@Configuration` 파일을 추가합니다.

추가적인 정보는 아래 링크를 참고해주세요.
https://springfox.github.io/springfox/docs/current/

```kotlin
@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun productApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.baegoony.springswaggerdemo"))
            .build()
            .apiInfo(this.metaInfo())
    }

    private fun metaInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("API 문서")
            .description("테스트 API 문서입니다.")
            .version("1.0")
            .termsOfServiceUrl("http://terms.com")
            .license("Apache")
            .licenseUrl("http://license.com")
            .contact(
                Contact(
                    "Baegoony",
                    "http://baegoon.kr",
                    "baegoony@gmail.com"
                )
            )
            .build()
    }
}
``` 


#### 2.3. 서버 실행 및 문서 페이지 들어가기

http://localhost:8080/swagger-ui.html


## 3. Swagger 주요 애노테이션

```kotlin
@Api(description = "팀 관리 API")
```

```kotlin
@ApiOperation("팀 생성 API", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
```

```kotlin
@ApiResponses(
    ApiResponse(code = 201, message = "생성완료"),
    ApiResponse(code = 400, message = "요청 똑바로 해라"),
    ApiResponse(code = 500, message = "서버가 맛탱이 갔음")
)
```

```kotlin
@ApiImplicitParams(
    ApiImplicitParam("페이지 번호", name = "page", dataType = "int", paramType = "query", defaultValue = "0"),
    ApiImplicitParam("페이지 사이즈", name = "size", dataType = "int", paramType = "query", defaultValue = "20"),
    ApiImplicitParam("정렬", name = "sort", dataType = "string", paramType = "query")
)
```

```kotlin
data class TeamBody(
    @ApiModelProperty("팀명", required = true, example = "A팀")
    val name: String,
    @ApiModelProperty("팀 브랜드", required = true, example = "나이키")
    val maker: String,
    @ApiModelProperty("팀 생성 연도", required = true, example = "2019")
    @field:Min(1900)
    val year: Int
)
```
