
function IconsForLegendpre2008() {
    var opta = "LOSS OF ENGINE POWER"; var optb = "LOSS OF ENGINE POWER(TOTAL) - MECH FAILURE/MALF"; var optc = "LOSS OF ENGINE POWER(TOTAL) - NONMECHANICAL";
    var optd = "LOSS OF ENGINE POWER(PARTIAL) - MECH FAILURE/MALF"; var opte = "LOSS OF ENGINE POWER(PARTIAL) - NONMECHANICAL";
    var engineArray = opta+','+optb+','+optc+','+optd+','+opte;
    var iconBase = 'https://maps.google.com/mapfiles/kml/shapes/';
    var icons = {
        terrain: {
            id: "IN FLIGHT COLLISION WITH TERRAIN/WATER",
            name: "Collision with Terrain",
            url: iconBase + 'mountains.png',
            scaledSize: new google.maps.Size(10, 10)
        },
        landing: {
            id: "FORCED LANDING",
            name: "Forced Landing",
            url: iconBase + 'airports.png',
            scaledSize: new google.maps.Size(10, 10)
        },
        object: {
            id:"IN FLIGHT COLLISION WITH OBJECT",
            name: "Collision with Object",
            url: iconBase + 'info_circle.png',
            scaledSize: new google.maps.Size(10, 10)
        },
        engine: {
            id: engineArray,
            name: "Engine Failure",
            url: iconBase + 'caution.png',
            scaledSize: new google.maps.Size(10, 10)
        },
        component: {
            id: "AIRFRAME/COMPONENT/SYSTEM FAILURE/MALFUNCTION",
            name: "component/system failure",
            url: iconBase + 'mechanic.png',
            scaledSize: new google.maps.Size(10, 10)
        },
        weather: {
            id: "IN FLIGHT ENCOUNTER WITH WEATHER",
            name: "Encounter with Weather",
            url: iconBase + 'rainy.png',
            scaledSize: new google.maps.Size(10, 10)
        },
        warning: {
            id: "LOSS OF CONTROL - IN FLIGHT",
            name: "Loss of Control",
            url: iconBase + 'arrow-reverse.png',
            scaledSize: new google.maps.Size(10, 10)
        },
        misc: {
            id: "x",
            name: "Other",
            url: iconBase + 'info.png',
            scaledSize: new google.maps.Size(10, 10)
        },
        close:{
            id:"close",
            name:"Close all markers",
            url:iconBase + 'forbidden.png',
            scaledSize: new google.maps.Size(10, 10)
        }
    };
    return icons;
}
function IconsForLegendpost2008() {
    var iconBase = 'http://maps.google.com/mapfiles/kml/paddle/';
    var icons = {
        personal_TaskPerf: {
            id: "Personnel issues-Task performance",
            name: "Personnel task performance",
            url: iconBase + 'A_blue.png',
            scaledSize: new google.maps.Size(10, 10)
        },
        personal_ActDec: {
            id: "Personnel issues-Action/decision",
            name: "Personnel Action/decision",
            url: iconBase + 'B_blue.png',
            scaledSize: new google.maps.Size(10, 10)
        },
        personal_Psy: {
            id:"Personnel issues-Psychological",
            name: "Psychological ",
            url: iconBase + 'C_blue.png',
            scaledSize: new google.maps.Size(10, 10)
        },
        Aircraft_OperPerf: {
            id: "Aircraft-Aircraft oper/perf/capability",
            name: "Aircraft oper/perf/capability",
            url: iconBase + 'D_blue.png',
            scaledSize: new google.maps.Size(10, 10)
        },
        Aircraft_System: {
            id: "Aircraft-Aircraft systems",
            name: "Aircraft systems",
            url: iconBase + 'E_blue.png',
            scaledSize: new google.maps.Size(10, 10)
        },
        Aircraft_PwrPlnt: {
            id: "Aircraft-Aircraft power plant",
            name: "Aircraft power plant",
            url: iconBase + 'F_blue.png',
            scaledSize: new google.maps.Size(10, 10)
        },
        enviro_Physical: {
            id: "Environmental issues-Physical environment",
            name: "Physical environment",
            url: iconBase + 'G_blue.png',
            scaledSize: new google.maps.Size(10, 10)
        },
        enviro_Conditions:{
            id:"Environmental issues-Conditions/weather/phenomena",
            name:"Weather Conditions/phenomena",
            url:iconBase + 'H_blue.png',
            scaledSize: new google.maps.Size(10,10)
        },
        notDetermined:{
            id:"Not determined",
            name:"Un-determined",
            url:iconBase + 'I_blue.png',
            scaledSize: new google.maps.Size(10,10)
        },
        misc: {
            id: "x",
            name: "Other / no data available",
            url: iconBase + 'G_blue.png',
            scaledSize: new google.maps.Size(10, 10)
        },
        close:{
            id:"close",
            name:"Close all markers",
            url:'https://maps.google.com/mapfiles/kml/shapes/forbidden.png',
            scaledSize: new google.maps.Size(10,10)
        }
    }
    return icons;
}
function IconsPre2008(z) {
    var iconBase = 'https://maps.google.com/mapfiles/kml/shapes/';
    var icons = {
        terrain: { name: "Collision with Terrain", url: iconBase + 'mountains.png', scaledSize: new google.maps.Size(20, 20)
        },
        landing: { name: "Forced Landing", url: iconBase + 'airports.png', scaledSize: new google.maps.Size(20, 20)
        },
        object: { name: "Collision with Object", url: iconBase + 'info_circle.png', scaledSize: new google.maps.Size(20, 20)
        },
        engine: { name: "Engine Failure", url: iconBase + 'caution.png', scaledSize: new google.maps.Size(20, 20)
        },
        component: { name: "component/system failure", url: iconBase + 'mechanic.png', scaledSize: new google.maps.Size(20, 20)
        },
        weather: { name: "Encounter with Weather", url: iconBase + 'rainy.png', scaledSize: new google.maps.Size(20, 20)
        },
        warning: { name: "Loss of Control", url: iconBase + 'arrow-reverse.png', scaledSize: new google.maps.Size(20, 20)
        },
        misc: { name: "misc", url: iconBase + 'info.png', scaledSize: new google.maps.Size(20, 20)
        }
    };
    return icons[z];
}
function IconsPost2008(z) {
    var iconBase = 'http://maps.google.com/mapfiles/kml/paddle/';
    var icons = {
        personal_TaskPerf: {name: "Personnel issues with task performance",
            url: iconBase + 'A_blue.png',
            scaledSize: new google.maps.Size(20, 20)
        },
        personal_ActDec: {name: "Personnel issues bad Action/decision",
            url: iconBase + 'B_blue.png',
            scaledSize: new google.maps.Size(20, 20)
        },
        personal_Psy: {name: "Psychological issues with Personnel",
            url: iconBase + 'C_blue.png',
            scaledSize: new google.maps.Size(20, 20)
        },
        Aircraft_OperPerf: {name: "Problem with aircraft oper/perf/capability",
            url: iconBase + 'D_blue.png',
            scaledSize: new google.maps.Size(20, 20)
        },
        Aircraft_System: {name: "Problem with aircraft systems",
            url: iconBase + 'E_blue.png',
            scaledSize: new google.maps.Size(20, 20)
        },
        Aircraft_PwrPlnt: {name: "Problem with aircraft power plant",
            url: iconBase + 'F_blue.png',
            scaledSize: new google.maps.Size(20, 20)
        },
        enviro_Physical: {name: "Issues with the physical environment",
            url: iconBase + 'G_blue.png',
            scaledSize: new google.maps.Size(20, 20)
        },
        enviro_Conditions:{name:"Issues with Conditions/weather/phenomena",
            url:iconBase + 'H_blue.png',
            scaledSize: new google.maps.Size(20,20)
        },
        notDetermined:{name:"Un-determined",
            url:iconBase + 'I_blue.png',
            scaledSize: new google.maps.Size(20,20)
        },
        misc: {name: "Other",
            url: iconBase + 'G_blue.png',
            scaledSize: new google.maps.Size(20, 20)
        }
    };
    return icons[z];
}