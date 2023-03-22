package js.training.kopring.config.filter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import js.training.kopring.exception.GlobalException
import js.training.kopring.model.dto.payload.response.BaseResponse
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter

@Slf4j
class ExceptionFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (exception: Exception) {
            logger.error(exception.stackTraceToString())
            when (exception) {
                is GlobalException -> writeErrorCode(exception, response)
                else -> writeErrorCode(HttpStatus.INTERNAL_SERVER_ERROR, response)
            }
        }
    }

    private fun writeErrorCode(exception: GlobalException, response: HttpServletResponse) {
        val errorResponse = BaseResponse.of(exception)

        writeErrorCode(errorResponse, response)
    }

    private fun writeErrorCode(status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR, response: HttpServletResponse) {
        val errorResponse = BaseResponse.of(status)

        writeErrorCode(errorResponse, response)
    }

    private fun writeErrorCode(payload: BaseResponse<Unit>, response: HttpServletResponse) {
        response.characterEncoding = "UTF-8"
        response.status = payload.status
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(objectMapper.writeValueAsString(payload))
    }
}