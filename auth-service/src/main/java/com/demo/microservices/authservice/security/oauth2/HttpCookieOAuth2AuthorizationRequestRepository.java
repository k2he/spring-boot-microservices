package com.demo.microservices.authservice.security.oauth2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;
import com.demo.microservices.authservice.util.CookieUtils;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.Getter;
/**
 * @author kaihe
 *
 */

@Component
public class HttpCookieOAuth2AuthorizationRequestRepository
    implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
  public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
  public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
  private static final int cookieExpireSeconds = 180;

  // TODO: need check why response Cookie is the not the one we set the
  // request. So this is
  // is a temp walk around. For some reason microservice doesn't work well
  // with Cookies
  private static String OAUTH2_AUTHORIZATION_REQUEST;
  
  @Getter
  private static String REDIRECT_URI_PARAM;

  @Override
  public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
    return CookieUtils.deserialize(OAUTH2_AUTHORIZATION_REQUEST, OAuth2AuthorizationRequest.class);

//    Optional<Cookie> cookie =
//        CookieUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
//    return cookie.map(c -> CookieUtils.deserialize(c, OAuth2AuthorizationRequest.class))
//        .orElse(null);
  }

  @Override
  public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
    return loadAuthorizationRequest(request);
  }

  @Override
  public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest,
      HttpServletRequest request, HttpServletResponse response) {
    if (authorizationRequest == null) {
      CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
      CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);

      // Temp Code
      REDIRECT_URI_PARAM = "";
      OAUTH2_AUTHORIZATION_REQUEST = "";

      return;
    }

    String value = CookieUtils.serialize(authorizationRequest);
    CookieUtils.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, value,
        cookieExpireSeconds);
    String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);
    if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
      CookieUtils.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin,
          cookieExpireSeconds);

      // Temp Code
      REDIRECT_URI_PARAM = redirectUriAfterLogin;
    }

    // Temp Code
    OAUTH2_AUTHORIZATION_REQUEST = value;
  }

  public void removeAuthorizationRequestCookies(HttpServletRequest request,
      HttpServletResponse response) {
    CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
    CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);

    // Temp Code
    REDIRECT_URI_PARAM = "";
    OAUTH2_AUTHORIZATION_REQUEST = "";

  }
}
