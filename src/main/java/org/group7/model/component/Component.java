package org.group7.model.component;

public abstract class Component {
    private ComponentEnum component;

    public void setComponent(ComponentEnum component) {
        this.component = component;
    }

    public String getTexture(){
        return component.getTexture();
    }
}
