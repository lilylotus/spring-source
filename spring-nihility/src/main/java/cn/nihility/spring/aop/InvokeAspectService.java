package cn.nihility.spring.aop;

import org.springframework.stereotype.Service;

@Service
public class InvokeAspectService {

    private AspectService aspectService;

    public InvokeAspectService(AspectService aspectService) {
        this.aspectService = aspectService;
    }

    public Integer multiplication(int x, int y) {
        return aspectService.multiplication(x, y);
    }

}
