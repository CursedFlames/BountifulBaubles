import os.path as path


ITEM_MODEL = """\
{{
\t"parent": "item/generated",
\t"textures": {{
\t\t"layer0": "{modid}:item/{itemname}"
\t}}
}}"""
ITEMBLOCK_MODEL = """\
{{
\t"parent": "{modid}:block/{itemname}"
}}"""
BLOCK_LOOT = """\
{{
\t"type": "minecraft:block",
\t"pools": [
\t\t{{
\t\t\t"name": "pool1",
\t\t\t"rolls": 1,
\t\t\t"entries": [
\t\t\t\t{{
\t\t\t\t\t"type": "minecraft:item",
\t\t\t\t\t"name": "{modid}:{itemname}"
\t\t\t\t}}
\t\t\t],
\t\t\t"conditions": [

\t\t\t]
\t\t}}
\t]
}}"""
BLOCK_LOOT_EXPLOSION_CONDITION = """\
\t\t\t\t{{
\t\t\t\t\t"condition": "minecraft:survives_explosion"
\t\t\t\t}}"""
def gen_json(copypasta, input_file, output_dir, modid, gen_type):
    print("Generating {}...".format(gen_type), end="", flush=True)
    count = 0
    with open(input_file) as conf:
        for line in conf:
            if len(line) == 0:
                continue
            line = line.rstrip()
            with open(path.join(output_dir, line+".json"), "w") as model:
                model.writelines(copypasta.format(modid=modid, itemname=line))
                count += 1
    print("\rGenerated {} {}  ".format(count, gen_type))

def gen_item_models(input_file, output_dir, modid):
    gen_json(ITEM_MODEL, input_file, output_dir, modid, "item models")

def gen_itemblock_models(input_file, output_dir, modid):
    gen_json(ITEMBLOCK_MODEL, input_file, output_dir, modid, "itemblock models")

def gen_block_loot(input_file, output_dir, modid):
    gen_json(BLOCK_LOOT, input_file, output_dir, modid, "block loot tables")
