package com.yrwl.common.config;

import com.fasterxml.classmate.TypeResolver;
import com.yrwl.annotation.Auth;
import com.yrwl.annotation.NoAuth;
import com.yrwl.common.model.ResponseObject;
import com.yrwl.constants.CommonConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.common.SwaggerPluginSupport;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

/**
 * @author  shentao
 * @date    2019-12-24
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.show}")
    private boolean swaggerShow;

    @Value("${server.host}")
    private String host;

    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public Docket createRestApi() {

        ParameterBuilder parameterBuilder = new ParameterBuilder();
        ParameterBuilder parameterBuilder1 = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<>();
        parameterBuilder.name(CommonConst.ACCESS_TOKEN).description("令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER - 2)
                .required(true).build();
        parameterBuilder1.name("appId").description("第三方唯一标识")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER - 1)
                .required(true).build();
        parameters.add(parameterBuilder1.build());
        parameters.add(parameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerShow)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Auth.class))
                .paths(PathSelectors.any())
                .build()
                .groupName("需要token验证")
                .globalOperationParameters(parameters)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(ResponseObject.class),
                                typeResolver.resolve(WildcardType.class)),
                        newRule(typeResolver.resolve(ResponseObject.class,
                                typeResolver.resolve(List.class)),
                                typeResolver.resolve(WildcardType.class)));
    }

    @Bean
    public Docket createPubRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerShow)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(NoAuth.class))
                .paths(PathSelectors.any())
                .build()
                .groupName("无需token验证")
                .alternateTypeRules(
                        newRule(typeResolver.resolve(ResponseObject.class),
                                typeResolver.resolve(WildcardType.class)),
                        newRule(typeResolver.resolve(ResponseObject.class,
                                typeResolver.resolve(List.class)),
                                typeResolver.resolve(WildcardType.class)));
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("COUPONS RESTful API")
                .description("优惠券接口文档")
                .contact(new Contact("Tao", host + "doc.html", "tao.shen@cnlaunch.com"))
                .version("1.0")
                .build();
    }
}
