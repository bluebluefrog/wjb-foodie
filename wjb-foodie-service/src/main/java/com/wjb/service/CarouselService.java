package com.wjb.service;

import com.wjb.pojo.Carousel;

import java.util.List;

public interface CarouselService {

    List<Carousel> queryAll(Integer isShow);
}
