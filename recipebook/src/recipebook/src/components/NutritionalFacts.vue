<template>
  <div class="vue-template">
    <div id="nutritionfacts">
        <table width="242" cellspacing="0" cellpadding="0">
            <tbody><tr>
                <td align="center" class="header">Nutrition Facts</td>
            </tr>
            <tr>
                <td><div class="serving">Per <span class="highlighted">1</span> Serving Size</div></td>
            </tr>
            <tr style="height: 7px">
                <td bgcolor="#000000"></td>
            </tr>
            <tr>
                <td>
                    <div class="line">
                    <div class="label">Calories <div class="weight">{{ facts.calories }}</div></div><div style="padding-top: 1px; float: right;" class="labellight">Calories from Fat <div class="weight">{{ facts.caloriesFromFat }}</div></div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><div class="line"><div class="dvlabel">% Daily Value<sup>*</sup></div></div></td>
            </tr>
            <tr>
                <td>
                    <div class="line">
                    <div class="label">Total Fat <div class="weight">{{ facts.totalFat }}g</div></div>
                    <div class="dv">{{ facts.totalFatPercent }}%</div>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="indent">
                    <div class="line">
                    <div class="labellight">Saturated Fat <div class="weight">{{ facts.saturatedFat }}g</div></div>
                    <div class="dv">{{ facts.saturatedFatPercent }}%</div>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="indent">
                    <div class="line">
                    <div class="labellight"><i>Trans</i> Fat <div class="weight">{{ facts.transFat }}g</div></div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="line">
                    <div class="label">Cholesterol <div class="weight">{{ facts.cholesterol }}mg</div></div>
                    <div class="dv">{{ facts.cholesterolPercent }}%</div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="line">
                    <div class="label">Sodium <div class="weight">{{ facts.sodium }}mg</div></div>
                    <div class="dv">{{ facts.sodiumPercent }}%</div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="line">
                    <div class="label">Total Carbohydrates <div class="weight">{{ facts.totalCarbohydrates }}g</div></div>
                    <div class="dv">{{ facts.totalCarbohydratesPercent }}%</div>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="indent">
                    <div class="line">
                    <div class="labellight">Dietary Fiber <div class="weight">{{ facts.dietaryFiber }}g</div></div>
                    <div class="dv">{{ facts.dietaryFiberPercent }}%</div>
                </div></td>
            </tr>
            <tr>
                <td class="indent">
                    <div class="line">
                    <div class="labellight">Sugars <div class="weight">{{ facts.sugars }}g</div></div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="line">
                    <div class="label">Protein <div class="weight">{{ facts.protein }}g</div>
                    </div>
                </div></td>
            </tr>
            <tr style="height: 7px">
                <td bgcolor="#000000"></td>
            </tr>
            <tr>
                <td>
                    <table cellspacing="0" cellpadding="0" border="0" class="vitamins">
                    <tbody>
                    <tr>
                        <td>Vitamin A &nbsp;&nbsp; {{ facts.vitaminAPercent }}%</td>
                        <td align="center">•</td>
                        <td align="right">Calcium &nbsp;&nbsp; {{ facts.calciumPercent }}%</td>
                    </tr>
                    <tr>
                        <td>Vitamin C &nbsp;&nbsp; {{ facts.vitaminCPercent }}%</td>
                        <td align="center">•</td>
                        <td align="right">Iron &nbsp;&nbsp; {{ facts.ironPercent }}%</td>
                    </tr>
                    <tr>
                        <td>Vitamin D &nbsp;&nbsp; {{ facts.vitaminDPercent }}%</td>
                        <td align="center">•</td>
                        <td align="right">Potassium &nbsp;&nbsp; {{ facts.potassiumPercent }}%</td>
                    </tr>                    
                    </tbody></table>
                </td>
            </tr>
            <tr>
                <td><div class="line">
                <div class="labellight">* Based on a regular 2000 calorie diet
                <br><i>Nutritional details are an estimate and should only be used as a guide for approximation.</i>
                </div>
                </div>
                </td>
            </tr>
        </tbody></table>
    </div>
  </div>
</template>

<script>
    export default {
        props: ["facts"],
    }
</script>

<style>
#nutritionfacts { 
    background-color:white; 
    padding:3px; width:244px; 
}
#nutritionfacts td { 
    color:black; 
    font-size:8pt; 
    padding:0; 
}
#nutritionfacts td.header { 
    font-size:28px; 
    white-space:nowrap; 
}        
#nutritionfacts div.label { 
    float:left; 
}
#nutritionfacts div.serving { 
    font-size:8pt; 
    text-align:center; 
}
#nutritionfacts div.weight { 
    display:inline; 
    padding-left:1px; 
}
#nutritionfacts div.dv { 
    display:inline; 
    float:right; 
}
#nutritionfacts table.vitamins td {  
    white-space:nowrap; 
    width:33%; 
}
#nutritionfacts div.line { 
    border-top:1px solid black; 
}
#nutritionfacts div.labellight { 
    float:left; 
}
#nutritionfacts .highlighted {
    padding:2px;
}
</style>