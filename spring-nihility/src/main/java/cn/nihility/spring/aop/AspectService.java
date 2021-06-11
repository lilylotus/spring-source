package cn.nihility.spring.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AspectService {

    private static final Logger log = LoggerFactory.getLogger(AspectService.class);

    public Integer multiplication(int x, int y) {
        int result = x * y;
        log.info("[{}] times [{}] is [{}]", x, y, result);
        return result;
    }

}
