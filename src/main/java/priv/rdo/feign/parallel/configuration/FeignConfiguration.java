package priv.rdo.feign.parallel.configuration;

import priv.rdo.feign.parallel.ParallelFeignApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = {ParallelFeignApplication.class})
public class FeignConfiguration {

}
