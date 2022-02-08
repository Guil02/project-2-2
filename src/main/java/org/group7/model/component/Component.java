package org.group7.model.component;

/**
 * This class is meant to be the super class of all the components that will exist in the project.
 * They contain a reference to their own object in the componentEnum so that the gui can easily access the texture path.
 */
public abstract class Component {
    private ComponentEnum component;

    public void setComponent(ComponentEnum component) {
        this.component = component;
    }


    /**
     * this method returns to the user a string with the path to the texture.
     * @return the path to the texture of the specified component.
     */
    public String getTexture(){
        return component.getTexture();
    }
}
