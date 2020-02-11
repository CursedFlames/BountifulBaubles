import os.path as path
from collections import defaultdict


copypasta = """\
{{
\t"replace": "false",
\t"values": [{}]
}}"""


def add_curios(input_file, output_dir, modid):
    print("Generating curios files...", end="", flush=True)
    type_count = 0
    item_count = 0
    items = defaultdict(set)
    with open(input_file) as conf:
        for line in conf:
            if len(line) == 0:
                continue
            line = line.rstrip().split(" ")
            for curiotype in line[1:]:
                items[curiotype].add(line[0])
            item_count += 1
        for curiotype in items:
            with open(path.join(output_dir, curiotype+".json"), "w") as outfile:
                list_string = ",".join(sorted(list(map(lambda a: '"'+modid+":"+a+'"', list(items[curiotype])))))
                outfile.writelines(copypasta.format(list_string))
                type_count += 1
    print("\rGenerated {} curios files with {} items".format(type_count, item_count))
