package com.cgvsu.Egor.triangle_rasterisation.color;


import com.cgvsu.Egor.triangle_rasterisation.math.Barycentric;

public interface Texture {
    public ColorRGB get(final Barycentric b);
}

