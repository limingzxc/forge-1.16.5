package com.wwwday.boson.tileentity;

import com.wwwday.boson.Boson;
import com.wwwday.boson.ModBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Boson.MOD_ID);

    public static final RegistryObject<TileEntityType<LightningChannelerTile>> LIGHTNING_CHANNELER_TILE =
            TILE_ENTITIES.register("lightning_channeler_tile", () -> TileEntityType.Builder.of(
                    LightningChannelerTile::new, ModBlocks.LIGHTNING_CHANNELER.get()).build(null));

    public static final RegistryObject<TileEntityType<ObsidianTrashTileEntity>> OBSIDIAN_TRASH_TILE =
            TILE_ENTITIES.register("obsidian_trash_tile", () -> TileEntityType.Builder.of(
                    ObsidianTrashTileEntity::new, ModBlocks.OBSIDIAN_TRASH.get()).build(null));

    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }
}
