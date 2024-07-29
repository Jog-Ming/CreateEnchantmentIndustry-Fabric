package io.github.fabricators_of_create.porting_lib;

import net.minecraft.resources.ResourceLocation;

public class PortingConstants {
	public static final String ID = "porting_lib";

	public static ResourceLocation id(String path) {
		return new ResourceLocation(ID, path);
	}
}
