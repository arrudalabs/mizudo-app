# 1 Mizu-do System

![diagram](https://www.plantuml.com/plantuml/svg/0/bPHDRnen48RlIFaFKmuLaGXxwQcdR21gWY3PPa6e9pHh3-3MQw_y0Q9L_tjiRw0qfKgxf_NCkpFFV7YlhKFZV2qlEk-4Oj9pWerpZVsOPGPtWxLm6rzvIuPfvKYv0TDrXjm8L5lf6vyDFrmL4fMRJoUPH4VM1TDo6DGe59b1au9VT2RvbyVvRBcudzqj9xUVRXz6lSjeByXOhNenHH-wK_7TNt4Dyt3iXdEk87f0hy1j20xkScyTrODkYD2-Bc-qLnpDlbS72TUjzIHWQ-XUme-B3eJd2DYp3GPTATHQ4XIu9iYRHWg6JcWL6RxY5Yqped60YaEkrbvYeYkCtWfE5b3ACtomyeh5A2Y5suFJgHOBMu5HBGpig8BAw5qmfs9-9zj5i4MG_3FGYAJOnXGH9epM2GOlWqlIKqRRCfP5VYPr7LDFTILaQaSgTf7_KVFPiRnTTjq6vhGLZCvavrMi1HrMQ2acVIA9RKGucA5a6wenUakdJG1AYs9eBQmpIGr2hRIfqtiVDcWtn05zO53kfPt03F5uH6dxW8oHjI3rEXp8iJDes8YubiNzww6Cmr1W5wuBVFPelddx_miI6uGmBXyVihlPh82G9H6q3A47RVRKdIT2Rk4zx8m8TouqEJ4KUJOUtGmFsF6Jk4JjYX-MuxMt_fipVTOM7EvZAkW8LvuAwpTqFsCDKTZUyXRd83sgVWdgenzE1TUaUFgRF0C0)

**Level 2: Container diagram**

Once you understand how your system fits in to the overall IT environment, a really useful next step is to zoom-in to the system boundary with a Container diagram. A "container" is something like a server-side web application, single-page application, desktop application, mobile app, database schema, file system, etc. Essentially, a container is a separately runnable/deployable unit (e.g. a separate process space) that executes code or stores data.

The Container diagram shows the high-level shape of the software architecture and how responsibilities are distributed across it. It also shows the major technology choices and how the containers communicate with one another. It's a simple, high-level technology focussed diagram that is useful for software developers and support/operations staff alike.

**Scope**: A single software system.

**Primary elements**: Containers within the software system in scope.
Supporting elements: People and software systems directly connected to the containers.

**Intended audience**: Technical people inside and outside of the software development team; including software architects, developers and operations/support staff.

**Notes**: This diagram says nothing about deployment scenarios, clustering, replication, failover, etc.