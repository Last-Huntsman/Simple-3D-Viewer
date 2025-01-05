package com.cgvsu.triangle_rasterisation.color;


import com.cgvsu.triangle_rasterisation.Baricentrics_Triangle.Barycentric;

public interface Texture {
    public ColorRGB get(final Barycentric b);
}

