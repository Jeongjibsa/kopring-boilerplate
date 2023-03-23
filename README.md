# Spring Boot With Kotlin boilerplate

## Environment

```
* Kotlin 1.7.22
* Java 17
* Spring Boot 3.0.4
* Spring Data JPA
* Spring Data Redis
* MapStruct 1.5.3
* JWT(io.jsonwebtoken:jjwt) 0.11.5
* Jooq 3.18.0
* Flyway 9.15.2
* UlidCreator 5.1.0 [GitHub](https://github.com/f4b6a3/ulid-creator)
* ...
```

## Notes

### 1. Spring Boot 3 관련

#### 1-1. Java Version

> `Java 17` 이상 필수

#### 1-2. Spring Security

##### 1) Spring Security 5.7 이후부터 `WebSecurityConfigurerAdapter`가 deprecated 됨.

```kotlin
    // AS-IS
class SecurityConfig : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        ...
    }
    ...
}

=>

// TO-BE
class SecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        ...
    }
}
```

##### 2) UsernameNotFoundException Handling

> `loadUserByUsername()`에서 `UsernameNotFoundException`을 throw했지만, `AuthenticationEntryPoint`가 handling하게됨.\
> 이는 `authenticate()` 호출 시 보안성의 이슈로 설계상 \
> `DaoAuthenticationProvider`가 `UsernameNotFoundException`을 catch하여 `BadCredentialsException`을 throw하게됨.

```java
// AbstractUserDetailsAuthenticationProvider.java
abstract class AbstractUserDetailsAuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    ...
        try {
            user = retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);
        } catch (UsernameNotFoundException ex) {
            this.logger.debug("Failed to find user '" + username + "'");
            if (!this.hideUserNotFoundExceptions) {
                throw ex;
            }
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
    ...
    }
}
```

> Solution :
> > 1. authenticate() 호출 전 service단에서 해당 유저 조회
> > 2. DaoAuthenticationProvider.setHideUserNotFoundExceptions(false) 설정

```kotlin
// 2. DaoAuthenticationProvider.setHideUserNotFoundExceptions(false) 설정
class SecurityConfig {

    ...

    @Bean("daoAuthenticationProvider")
    @Qualifier
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val daoAuthenticationProvider = DaoAuthenticationProvider()
        daoAuthenticationProvider.setUserDetailsService(authService)
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder())
        daoAuthenticationProvider.isHideUserNotFoundExceptions = false
        return daoAuthenticationProvider
    }
}
```

### 2. Hibernate 6 관련

#### 1-1. `@Type` 어노테이션이 deprecated 됨에 따른 대체 방식 [참고링크](https://in.relation.to/2022/05/12/orm-uuid-mapping/)

> 1.`@Converter`(jakarta.persistence) \
> 단, AttributeConverter를 상속받는 경우 식별자에 사용될 수 없음.

> 2.`@JdbcTypeCode`(org.hibernate.annotations) \
> ex) `UUID`를 PK로 사용 시, DB상에 `BINARY`가 아닌 `CHAR`타입으로 저장하기 위해 `@JdbcTypeCode`사용

### 기타

#### 1. Plugin

- Java Annotation 처리를 위한 필수 Plugin
  > kotlin("kapt") version "1.7.22"
- all-open 적용
  > kotlin("plugin.spring") version "1.7.22"
  ```gradle
  allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
  }
  ```
- NoArgsConstructor 자동생성
  > kotlin("plugin.jpa") version "1.7.22" \
  > `@Entity`, `@Embeddable`, `@MappedSuperclass` 어노테이션 사용 시 자동생성됨

#### 2. Jackson Object Mapper

- Kotlin 사용을 위한 별도 모듈 추가 :
  > implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

### References

* https://spoqa.github.io/2022/08/16/kotlin-jpa-entity.html