package indianpoker.security;

import indianpoker.domain.user.User;
import indianpoker.exception.UnAuthenticationException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

// http://addio3305.tistory.com/75
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    // supportParameter의 리턴 값이 true이면 resolveArgument 실행
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        User sessionUser = SessionUtil.getUserFromSession(webRequest);
        if (sessionUser != null) {
            return sessionUser;
        }

        LoginUser loginUser = parameter.getParameterAnnotation(LoginUser.class);
        if (loginUser.required())
            throw new UnAuthenticationException("YOU'RE REQUIRED LOGIN!");

        return sessionUser;
    }
}
