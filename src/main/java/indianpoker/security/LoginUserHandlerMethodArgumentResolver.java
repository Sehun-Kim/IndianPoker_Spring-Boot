package indianpoker.security;

import indianpoker.exception.UnAuthenticationException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import support.util.SessionUtil;

// http://addio3305.tistory.com/75
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    // supportParameter의 리턴 값이 true이면 resolveArgument 실행
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return SessionUtil.getUserFromSession(webRequest)
                .orElseThrow(UnAuthenticationException::new);
    }
}
