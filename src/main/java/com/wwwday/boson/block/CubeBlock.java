package com.wwwday.boson.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import net.minecraft.block.AbstractBlock.Properties;

public class CubeBlock extends Block {
    public CubeBlock(){super(Properties.of(Material.STONE).strength(10));}
}
