
function pre2008Switch(initialEvent) {
    switch (initialEvent){
        case "IN FLIGHT COLLISION WITH TERRAIN/WATER":
            z="terrain";
            break;
        case "FORCED LANDING":
            z="landing";
            break;
        case "IN FLIGHT COLLISION WITH OBJECT":
            z = "object";
            break;
        case "LOSS OF ENGINE POWER":
        case "LOSS OF ENGINE POWER(TOTAL) - MECH FAILURE/MALF":
        case "LOSS OF ENGINE POWER(TOTAL) - NONMECHANICAL":
        case "LOSS OF ENGINE POWER(PARTIAL) - MECH FAILURE/MALF":
        case "LOSS OF ENGINE POWER(PARTIAL) - NONMECHANICAL":
            z= "engine";
            break;
        case "AIRFRAME/COMPONENT/SYSTEM FAILURE/MALFUNCTION":
            z="component";
            break;
        case "IN FLIGHT ENCOUNTER WITH WEATHER":
            z="weather";
            break;
        case "LOSS OF CONTROL - IN FLIGHT":
            z="warning";
            break;
        default:
            z="misc";
    }
    return z;
}
function post2008Switch(initialEvent) {
    switch (true) {
        case /Personnel issues-Task performance/.test(initialEvent):
            z = "personal_TaskPerf";
            break;
        case /Personnel issues-Action\/decision/.test(initialEvent):
            z = "personal_ActDec";
            break;
        case /Personnel issues-Psychological/.test(initialEvent):
            z = "personal_Psy";
            break;
        case /Aircraft-Aircraft oper\/perf\/capability/.test(initialEvent):
            z = "Aircraft_OperPerf";
            break;
        case /Aircraft-Aircraft systems/.test(initialEvent):
            z = "Aircraft_System";
            break;
        case /Aircraft-Aircraft power plant/.test(initialEvent):
            z = "Aircraft_PwrPlnt";
            break;
        case /Environmental issues-Physical environment/.test(initialEvent):
            z = "enviro_Physical";
            break;
        case /Environmental issues-Conditions\/weather\/phenomena/.test(initialEvent):
            z = "enviro_Conditions";
            break;
        case /Not determined/.test(initialEvent):
            z = "notDetermined";
            break;
        default:
            z = "misc";
    }
    return z;
}
function post2008Switch2(occurrence) {
    switch (occurrence) {
        case "Personnel issues-Task performance":
            z = "personal_TaskPerf";
            break;
        case "Personnel issues-Action/decision":
            z = "personal_ActDec";
            break;
        case "Personnel issues-Psychological":
            z = "personal_Psy";
            break;
        case "Aircraft-Aircraft oper/perf/capability":
            z = "Aircraft_OperPerf";
            break;
        case "Aircraft-Aircraft systems":
            z = "Aircraft_System";
            break;
        case "Aircraft-Aircraft power plant":
            z = "Aircraft_PwrPlnt";
            break;
        case "Environmental issues-Physical environment":
            z = "enviro_Physical";
            break;
        case "Environmental issues-Conditions/weather/phenomena":
            z = "enviro_Conditions";
            break;
        case "Not determined":
            z = "notDetermined";
            break;
        default:
            z = "misc";
    }
    return z;
}
