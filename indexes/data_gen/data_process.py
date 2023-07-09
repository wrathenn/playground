import json
from typing import List

import pydantic
from pydantic import BaseModel


class Research(BaseModel):
    n: int
    b_btree: float
    b_mix: float
    s_btree: float
    s_mix: float
    c_gin: float
    c_mix: float


if __name__ == '__main__':
    with open('out/research_500000.json', 'r') as f:
        data = json.load(f)

    researches: List[Research] = pydantic.parse_obj_as(List[Research], data)
    print(researches)

    n_values = []
    begin_btree_values = []
    begin_mix_values = []
    start_btree_values = []
    start_mix_values = []
    contains_gin_values = []
    contains_mix_values = []

    for r in researches:
        n_values.append(r.n)
        begin_btree_values.append(r.b_btree)
        begin_mix_values.append(r.b_mix)
        start_btree_values.append(r.s_btree)
        start_mix_values.append(r.s_mix)
        contains_gin_values.append(r.c_gin)
        contains_mix_values.append(r.c_mix)

    n_values = list(map(str, n_values))
    begin_btree_values = list(map(str, begin_btree_values))
    begin_mix_values = list(map(str, begin_mix_values))
    start_btree_values = list(map(str, start_btree_values))
    start_mix_values = list(map(str, start_mix_values))
    contains_gin_values = list(map(str, contains_gin_values))
    contains_mix_values = list(map(str, contains_mix_values))

    print(json.dumps({'values': n_values}).lstrip('{').rstrip('}') + ',');
    print('По ключу:')
    print(json.dumps({'values': begin_btree_values}).lstrip('{').rstrip('}') + ',')
    print(json.dumps({'values': begin_mix_values}).lstrip('{').rstrip('}') + ',')
    print('По началу:')
    print(json.dumps({'values': start_btree_values}).lstrip('{').rstrip('}') + ',')
    print(json.dumps({'values': start_mix_values}).lstrip('{').rstrip('}') + ',')
    print('По содержанию слова:')
    print(json.dumps({'values': contains_gin_values}).lstrip('{').rstrip('}') + ',')
    print(json.dumps({'values': contains_mix_values}).lstrip('{').rstrip('}') + ',')