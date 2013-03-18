import ms.aurora.api.plugin.Plugin;
import ms.aurora.api.plugin.PluginManifest;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author rvbiljouw
 */
@PluginManifest(name = "Paint Debug", author = "rvbiljouw", version = 1.0)
public class PaintDebug extends Plugin {
    private NpcPaint npcPaint = new NpcPaint();
    private MousePaint mousePaint = new MousePaint();
    private ObjectPaint objectPaint = new ObjectPaint();
    private PositionPaint positionPaint = new PositionPaint();
    private AnimationPaint animationPaint = new AnimationPaint();
    private InventoryPaint inventoryPaint = new InventoryPaint();

    private boolean npcPaintActive = false;
    private boolean mousePaintActive = false;
    private boolean objectPaintActive = false;
    private boolean positionPaintActive = false;
    private boolean animationPaintActive = false;
    private boolean inventoryPaintActive = false;


    private JMenu paint;

    @Override
    public void startup() {
        paint = new JMenu("Debugging");

        JCheckBoxMenuItem npcs = new JCheckBoxMenuItem("Draw NPCs");
        npcs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!npcPaintActive) {
                    getSession().getPaintManager().register(npcPaint);
                } else {
                    getSession().getPaintManager().deregister(npcPaint);
                }
                npcPaintActive = !npcPaintActive;
            }
        });
        paint.add(npcs);

        JCheckBoxMenuItem objects = new JCheckBoxMenuItem("Draw Objects");
        objects.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!objectPaintActive) {
                    getSession().getPaintManager().register(objectPaint);
                } else {
                    getSession().getPaintManager().deregister(objectPaint);
                }
                objectPaintActive = !objectPaintActive;
            }
        });
        paint.add(objects);

        JCheckBoxMenuItem inventory = new JCheckBoxMenuItem("Draw Inventory");
        inventory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!inventoryPaintActive) {
                    getSession().getPaintManager().register(inventoryPaint);
                } else {
                    getSession().getPaintManager().deregister(inventoryPaint);
                }
                inventoryPaintActive = !inventoryPaintActive;
            }
        });
        paint.add(inventory);

        JCheckBoxMenuItem animation = new JCheckBoxMenuItem("Draw Animation");
        animation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!animationPaintActive) {
                    getSession().getPaintManager().register(animationPaint);
                } else {
                    getSession().getPaintManager().deregister(animationPaint);
                }
                animationPaintActive = !animationPaintActive;
            }
        });
        paint.add(animation);

        JCheckBoxMenuItem position = new JCheckBoxMenuItem("Draw Position");
        position.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!positionPaintActive) {
                    getSession().getPaintManager().register(positionPaint);
                } else {
                    getSession().getPaintManager().deregister(positionPaint);
                }
                positionPaintActive = !positionPaintActive;
            }
        });
        paint.add(position);

        JCheckBoxMenuItem mouse = new JCheckBoxMenuItem("Draw Mouse");
        mouse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mousePaintActive) {
                    getSession().getPaintManager().register(mousePaint);
                } else {
                    getSession().getPaintManager().deregister(mousePaint);
                }
                mousePaintActive = !mousePaintActive;
            }
        });
        paint.add(mouse);

        getSession().registerMenu(paint);
    }

    @Override
    public void execute() {
    }

    @Override
    public void cleanup() {
        getSession().deregisterMenu(paint);
    }



}