# 2 Deployment

![diagram](https://www.plantuml.com/plantuml/svg/0/fPHBJnin4CVlI7o7QIuD4cGlFVNKa50WIaXWWr1FqSHsTbsyzjQF1AZuxXrxymA5IbMpXwozy_h_PknlpgFreLB7Hv-aPYfm0QNtjVkQPHPNdKBwCio34vOPxONs7MQg3BcLg9Sgr27hVJcRADJ-VZJC57hXF6tD-g9MvhcA3dMAVdmqxFuOtqzd3zVJgzdmulBYfjy-YVixqzcDuQ9TcVafjEufvMS7j91A01TBoKIBzhjrJUlhSGx6GbTpQoHldVm-FWBwzQX6b5hOTkD7zgCc0Bb5xyb3D_rPivHSEF2bW95y2MVSGFxilAXW4JJpqcXKqZ-3Dn0hSR2K6Asb1OhHQL7bhuUA9phrhlgu2bxOcFD1QcvMBXjA7Pwo4R9nlgtxVPIv9KjXevUOmtcpYa4kZIb8JQ-qfY9r8_CYbKBuBgrOc2VQoE625_GbnmLQUGh2iyuspLj4haQAc4iT8qwmI92KP1ZLnsG3N6BEhAmzeEO4kmWAxN_pMv6ajS34CLRr-W7EKbZp62ZZLNffRT-0eqTWP4XWehx3876dY5nl0zh7u5ffBBIX0iitcWzpmbh6kPjSRvWa6dknjZJ-IY82G1ZauvliQZgTv40Hj_hVPYH2wzQ_wyXU2_f2oMK46rj0XzXB1kiZckhRxunhUbp77kyHsQT_65zjd2-iSBzKbDaiyjjXAeNpiu-GzUTj7XNqgKTpTEdStWc53GpOR4FEIb5X_9fxGsaWtZDWHI6TjyaQf5uOMwqRMA8h1GSC94xxjGfWLl2uHELE0HaJpe4oXTiC_zi9Ym_LqZ3UtX8xcYQHMvzVEkg0pWdlOfYjMtDoFl3P6--DqoPDCqaZV2Ifr7rWg5HqZmH-EgERmTXufIp_vfAi4_exWTp1Wgw9D0qhAxtO-0qctMpGF--rqlNxJMXED_GV)

**Deployment diagram**

A deployment diagram allows you to illustrate how containers in the static model are mapped to infrastructure. This deployment diagram is based upon a UML deployment diagram, although simplified slightly to show the mapping between containers and deployment nodes. A deployment node is something like physical infrastructure (e.g. a physical server or device), virtualised infrastructure (e.g. IaaS, PaaS, a virtual machine), containerised infrastructure (e.g. a Docker container), an execution environment (e.g. a database server, Java EE web/application server, Microsoft IIS), etc. Deployment nodes can be nested.

**Scope**: A single software system.

**Primary elements**: Deployment nodes and containers within the software system in scope.

**Intended audience**: Technical people inside and outside of the software development team; including software architects, developers and operations/support staff.