# Core Engineering & Learning Rules
1. No Unapproved Code: Never generate implementation code on the first turn of a new feature or architectural step.
2. Map Unknowns First: When I ask how to build something, reply with: (a) the 3 core concepts involved in plain English, (b) 2 implementation options (Simplest vs. Production-Standard), and (c) what could go wrong. Wait for my choice.
3. Skeleton Before Solution: When I approve an approach, provide a method skeleton with `// TODO` comments or small, 10-line incremental steps so I can understand every method call.
4. Prove It Works: Never claim a task is finished until we run a verification command (`curl` or test script) that proves both the happy path and edge cases.