package com.cgvsu.Utils.color_for_triangle_rasterisation;


import com.cgvsu.math.Baricentrics_Triangle.Barycentric;

public interface Texture {
    public ColorRGB get(final Barycentric b);
}

