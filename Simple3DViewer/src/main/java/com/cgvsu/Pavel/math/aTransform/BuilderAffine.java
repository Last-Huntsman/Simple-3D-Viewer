package com.cgvsu.Pavel.math.aTransform;

public class BuilderAffine {
    private final MegaTransform megaTransform = new MegaTransform();

    public BuilderAffine scale(float sx, float sy, float sz) {
        megaTransform.add(new Scale(sx, sy, sz));
        return this;
    }

    public BuilderAffine scaleX(float sx) {
        megaTransform.add(new Scale(sx, 0, 0));
        return this;
    }

    public BuilderAffine scaleY(float sy) {
        megaTransform.add(new Scale(0, sy, 0));
        return this;
    }

    public BuilderAffine scaleZ(float sz) {
        megaTransform.add(new Scale(0, 0, sz));
        return this;
    }


    public BuilderAffine rotateX(float a) {
        megaTransform.add(new Rotate(a, AXIS.X));
        return this;
    }

    public BuilderAffine rotateY(float a) {
        megaTransform.add(new Rotate(a, AXIS.Y));
        return this;
    }

    public BuilderAffine rotateZ(float a) {
        megaTransform.add(new Rotate(a, AXIS.Z));
        return this;
    }

    public BuilderAffine rotate(float a, AXIS axis) {
        megaTransform.add(new Rotate(a, axis));
        return this;
    }

    public BuilderAffine translateX(float a) {
        megaTransform.add(new Translate(a, 0, 0));
        return this;
    }

    public BuilderAffine translateY(float a) {
        megaTransform.add(new Translate(0, a, 0));
        return this;
    }

    public BuilderAffine translateZ(float a) {
        megaTransform.add(new Translate(0, 0, a));
        return this;
    }

    public BuilderAffine translate(float a, AXIS axis) {
        megaTransform.add(new Translate(a, axis));
        return this;
    }

    public BuilderAffine translate(float tx, float ty, float tz) {
        megaTransform.add(new Translate(tx, ty, tz));
        return this;
    }

    public BuilderAffine apply(MegaTransform mt) {
        megaTransform.add(mt);
        return this;
    }

    public IAffine build() {
        return new ConstantTransform(megaTransform.vertexTransform());
    }
}