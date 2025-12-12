import pulp

def process_input(filename):
    with open(filename) as file:
        input = file.read().splitlines()

    machines = []

    for line in input:
        machine = {}
        tokens = line.split()

        buttons = [tuple(int(x) for x in token[1:-1].split(',')) for token in tokens[1:-1]]
        machine['buttons'] = buttons

        joltage = tuple(int(x) for x in tokens[-1][1:-1].split(','))
        machine['joltage'] = joltage

        machines.append(machine)

    return machines

def configure_machines():
    total_presses = 0
    for machine in machines:
        presses = configure_machine_pulp(machine)
        #print('Machine',machine['joltage'],':',presses,'presses')
        total_presses += presses
    return total_presses

def configure_machine_pulp(machine):
    buttons = machine['buttons']
    joltage = machine['joltage']

    # Build reverse map: for each counter, which buttons increment it?
    counters = [[] for _ in range(len(joltage))]
    for b, button in enumerate(buttons):
        for c in button:
            counters[c].append(b)

    # Create LP problem (minimization)
    prob = pulp.LpProblem("MachineConfig", pulp.LpMinimize)

    # Decision vars: number of presses for each button (non-negative integers)
    btn = [pulp.LpVariable(f"btn_{b}", lowBound=0, cat='Integer')
           for b in range(len(buttons))]

    # Add constraints: each counter must reach required joltage
    for c, button_list in enumerate(counters):
        prob += pulp.lpSum(btn[b] for b in button_list) == joltage[c]

    # Objective: minimize total number of presses
    prob += pulp.lpSum(btn)

    # Solve
    prob.solve(pulp.PULP_CBC_CMD(msg=False))

    # Extract result
    presses = sum(v.value() for v in btn)

    return presses


#-----------------------------------------------------------------------------------------

filename = 'inputDay2510Solver.txt'

machines = process_input(filename)

presses = configure_machines()

print()
print('Button presses:', presses)