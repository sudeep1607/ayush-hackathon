package com.fts.hibernate.common;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@SuppressWarnings("serial")
public abstract class BaseEntity implements Serializable
{
     
    @Id 
    @GeneratedValue
    protected Long id;

    public Long getId()
    {
        return id;
    }

    public void setId(Long databaseId)
    {
        this.id = databaseId;
    }

    /**
     * The current implementation makes a loose check for class equivalence. We cannot make a simple class equality check as CGLIB creates a proxy
     * class with a difference name. Because of the proxy class, we need to prevent referring directly to non final fields from final methods and use
     * getters instead.
     */
    @Override
    public final boolean equals(Object other)
    {
        if (other instanceof BaseEntity)
        {
            return (other != null && getId() != null && getId().equals(((BaseEntity) other).getId()))
                    && ((other.getClass().isAssignableFrom(this.getClass()))
                            || (this.getClass().isAssignableFrom(other.getClass())));
        }

        return false;
    }

    @Override
    public final int hashCode()
    {
        return 0;
    }

}
