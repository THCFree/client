package me.finz0.osiris.event.events;

import me.finz0.osiris.event.OsirisEvent;
import net.minecraft.entity.Entity;

public class EntityUseTotemEvent extends OsirisEvent {
    private Entity entity;

    public EntityUseTotemEvent(Entity entity) {
        super();
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}