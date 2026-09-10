package modernmods.biggerreactorsrevived.registries;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;

import java.util.ArrayList;
import java.util.List;

public record ScriptedEntry<T>(ExtraCodecs.TagOrElementLocation location, T value) {

    public static ExtraCodecs.TagOrElementLocation parseLocation(String location) {
        if (location.startsWith("#")) {
            return new ExtraCodecs.TagOrElementLocation(Identifier.parse(location.substring(1)), true);
        }
        return new ExtraCodecs.TagOrElementLocation(Identifier.parse(location), false);
    }

    public static <E> List<E> resolve(Registry<E> registry, ExtraCodecs.TagOrElementLocation location) {
        final var resolved = new ArrayList<E>();
        if (location.tag()) {
            registry.get(TagKey.create(registry.key(), location.id()))
                    .ifPresent(holders -> holders.forEach(holder -> resolved.add(holder.value())));
        } else if (registry.containsKey(location.id())) {
            resolved.add(registry.getValue(location.id()));
        }
        return resolved;
    }
}
