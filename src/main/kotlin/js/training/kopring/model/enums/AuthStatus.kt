package js.training.kopring.model.enums

import js.training.kopring.model.dto.payload.BasePayload

enum class AuthStatus(
    override val status: Int,
    override val message: String,
    val describe: String,
) : BasePayload {
    SIGN_UP_SUCCESS(200, "Success to SignUp", "회원가입에 성공했습니다."),
    SIGN_IN_SUCCESS(200, "Success to SignIn", "로그인에 성공했습니다."),
    REISSUE_SUCCESS(200, "Success to reissue", "토큰 재발급에 성공했습니다."),
    DELETE_USER_SUCCESS(200, "Success to delete user", "회원탈퇴에 성공했습니다."),
    DUPLICATE_USER(400, "Duplicate user", "이미 존재하는 사용자입니다."),
    USER_NOT_FOUND(404, "User Not Found", "사용자를 찾을 수 없습니다."),
    UNAUTHORIZED(401, "Unauthorized Request", "유효하지 않은 요청입니다."),
    JWT_EXPIRED(401, "Jwt Token Expired", "토큰이 만료되었습니다."),
    JWT_SIGNATURE(401, "Invalid Signature", "토큰 시그니쳐가 손상되어있습니다."),
    JWT_VALIDATE_FAIL(401, "Token Validate Failed", "토큰 검증에 실패했습니다."),
    FORBIDDEN(403, "Not Enough Permission", "페이지에 접근할 권한이 없습니다."),
    UNEXPECTED_TOKEN(500, "Unexpected Token Exception", "토큰 검증 과정에서 예기치 못한 문제가 발생했습니다.")
}