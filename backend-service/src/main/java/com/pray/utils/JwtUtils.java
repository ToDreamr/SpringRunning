package com.pray.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pray.entity.po.LoginUser;
import com.pray.exception.ErrorCode;
import com.pray.exception.ThrowUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * JwtUtils
 * @author 春江花朝秋月夜
 * @since 2023/8/24
 */
@Component
public class JwtUtils {

    @Value("${spring.security.jwt.key}")
    public  String SECRET_KEY;//密钥
    @Value("${spring.security.jwt.expire}")
    private long expireTime;//过期时间

    /**
     * 解析原始Token为DecodedJWT
     * @param headToken
     * @return
     */
    public DecodedJWT resolve(String headToken){
        //undefined不是null
        if (headToken.isBlank()){
            return null;
        }
        Algorithm algorithm=Algorithm.HMAC256(SECRET_KEY);
        JWTVerifier jwtVerifier=JWT.require(algorithm).build();

        try {
            DecodedJWT verify = jwtVerifier.verify(headToken);
            Date expires = verify.getExpiresAt();
            //判断token是否过期同时返回
            return new Date().after(expires) ? null:verify;
        }catch (JWTDecodeException e){
            //抛出异常交给下一级处理
            ThrowUtils.merelyThrow(ErrorCode.SYSTEM_ERROR,"解析失败");
            return null;
        }
    }

    /**
     * 携带username，expireTime,nowTime,algorithm
     * @param user
     * @param username
     * @return
     */
    public String createJwtWithKeyParam(UserDetails user,String username){
        Algorithm algorithm=Algorithm.HMAC256(SECRET_KEY);
        //携带下面的信息
        return JWT.create()
                .withClaim("username",username)
                .withExpiresAt(expireTime())
                .withIssuedAt(Instant.from(LocalDateTime.now()))
                .sign(algorithm);//签名
    }

    /**
     * 设置过期时间
     * @return
     */
    public Date expireTime(){
        long now = System.currentTimeMillis();
        long future = now + TimeUnit.HOURS.toMillis(this.expireTime);
        return new Date(future);
    }

    /**
     * 解析颁发的jwt，将内容封装成UserDetails对象
     * @param jwt 已经解析的jwt对象
     * @return User
     */
    public LoginUser convertJwtIntoLoginUser(DecodedJWT jwt){
        Map<String, Claim> claims = jwt.getClaims();
        LoginUser user = new LoginUser();
        user.setUsername(String.valueOf(claims.get("username")));
        return user;
    }
}
