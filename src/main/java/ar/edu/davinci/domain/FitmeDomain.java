package ar.edu.davinci.domain;

import java.io.Serializable;

public abstract class FitmeDomain<T extends Serializable>{

    public abstract T getId();
    public abstract void setId(T id);
}
