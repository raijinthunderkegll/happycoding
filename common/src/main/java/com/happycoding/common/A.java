package com.happycoding.common;

import org.springframework.beans.BeanUtils;

public class A {
    private String aA;

    public String getaA() {
        return aA;
    }

    public void setaA(String aA) {
        this.aA = aA;
    }

    public static void main(String[] args) {
        A a = new A();
        a.setaA("1");
        A b = new A();
        BeanUtils.copyProperties(a,b);
        System.out.println(b.getaA());
    }
}
