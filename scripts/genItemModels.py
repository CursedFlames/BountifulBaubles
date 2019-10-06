import os.path as path


copypasta = """\
{{
\t"parent": "item/handheld",
\t"textures": {{
\t\t"layer0": "{modid}:item/{itemname}"
\t}}
}}"""


def gen_item_models(input_file, output_dir, modid):
    print("Generating item models...", end="", flush=True)
    count = 0
    with open(input_file) as conf:
        for line in conf:
            if len(line) == 0:
                continue
            line = line.rstrip()
            with open(path.join(output_dir, line+".json"), "w") as model:
                model.writelines(copypasta.format(modid=modid, itemname=line))
                count += 1
    print("\rGenerated {} item models  ".format(count))
