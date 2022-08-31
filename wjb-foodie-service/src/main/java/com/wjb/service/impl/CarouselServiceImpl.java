package com.wjb.service.impl;

import com.wjb.mapper.CarouselMapper;
import com.wjb.pojo.Carousel;
import com.wjb.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {


    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    public List<Carousel> queryAll(Integer isShow) {

        Example example = new Example(Carousel.class);

        //通过传入参数(对象属性)排序(默认asc)
        example.orderBy("sort").desc();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isShow", isShow);

        List<Carousel> carousels = carouselMapper.selectByExample(example);

        return carousels;
    }

}
