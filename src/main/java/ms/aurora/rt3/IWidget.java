package ms.aurora.rt3;

/**
 * @author Rick
 */
public interface IWidget {

    String getText();

    String[] getActions();

    IWidget[] getChildren();

    IWidget getDisabledComponent();

    int getRotationX();

    int getRotationY();

    int getRotationZ();

    int getOffsetX();

    int getOffsetY();

    int getModelZoom();

    int getScrollMaxHorizontal();

    int getBorderThickness();

    int getItemId();

    int getComponentIndex();

    int getTextureId();

    int getY();

    int getItemStackSize();

    int getX();

    int getTextColor();

    int getShadowColor();

    int getParentId();

    int getFontId();

    int getHeight();

    int getWidth();

    int[][] getOpcodes();

    String getSelectedAction();

    int getScrollMaxVertical();

    int getUid();

    int getType();

    int[] getInventoryItems();

    int[] getInventoryStackSizes();

    int getBoundsIndex();

    int getModelId();

    int getModelType();
}
