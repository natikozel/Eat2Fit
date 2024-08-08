package com.example.finalproj.util

import com.example.finalproj.R

data class InformationPage(
    val infoPageId: String,
    val imageId: Int,
    val title: String,
    val openingQuestion: String,
    val sections: List<Section>
)

data class Section(
    val subtitle: String,
    val text: String
)


object InformationPageManager {
    val informationPages = listOf(
        InformationPage(
            infoPageId = "1",
            imageId = R.drawable.calorie_deficit,
            title = "Calorie Deficit",
            openingQuestion = "Ever wandered what calorie deficit means? keep reading",
            sections = listOf(
                Section(
                    subtitle = "What is a Calorie Deficit?",
                    text = "A calorie deficit occurs when you consume fewer calories than you burn. People often use this method to lose weight and maintain their weight loss.\n\nIf you want to lose weight through a calorie deficit, we recommend tracking your current eating habits before making any changes."
                ),
                Section(
                    subtitle = "Determining Your Daily Calorie Requirement",
                    text = "First, you need to determine your daily calorie requirement. Once you have your daily calorie requirement, subtract your calorie deficit goal from that number. For instance, if your daily calorie need is 1,800 calories and you want to achieve a 500-calorie deficit, your new daily calorie target would be 1,300 calories (1,800 - 500 = 1,300)."
                ),
                Section(
                    subtitle = "What is a Safe Calorie Deficit?",
                    text = "A daily 500-calorie deficit typically allows you to lose about 500 grams per week, and possibly a bit more.\n\nAlternatively, you can aim for a smaller calorie deficit of 200-300 calories per day and increase your daily exercise.\n\nAlthough losing one pound per week might seem slow, gradual weight loss is more likely to be sustainable in the long term. Additionally, your body can more easily adjust to a smaller calorie deficit.\n\nStarting with a large calorie deficit can lead to unpleasant side effects, such as fatigue and headaches."
                ),
                Section(
                    subtitle = "Tips for Losing Weight on a Calorie Deficit",
                    text = "Stay Hydrated: Sometimes, feelings of hunger are actually your body's way of signaling thirst. Drinking plenty of water also helps your body adjust to consuming fewer calories.\n\nGet Enough Protein: Adequate protein intake helps you feel fuller longer and supports muscle retention, which keeps your metabolism active.\n\nEat Your Fruits and Vegetables: Aim for five servings of fruits and vegetables per day. The bulk and water content in these foods help you feel full, and the act of chewing can slow down your eating, making you feel more satiated.\n\nTake a Multivitamin: As you transition to a calorie-deficit diet, a multivitamin can help fill in any nutritional gaps. Consult with your healthcare provider to find the right multivitamin for you."
                )
            )
        ),
        InformationPage(
            infoPageId = "2",
            imageId = R.drawable.super_food,
            title = "Super Foods",
            openingQuestion = "“Super Foods”? Are they like Superman?",
            sections = listOf(
                Section(
                    subtitle = "Understanding the Term \"Superfood\"",
                    text = "\"Superfood\" is a popular term in the nutrition world, often used to describe foods that are exceptionally nutrient-dense. While there is no scientific definition for what makes a food a superfood, the term is widely embraced—after all, who wouldn't want their food to be \"super\"?"
                ),
                Section(
                    subtitle = "Watercress",
                    text = "This lesser-known vegetable is recognized by the Centers for Disease Control and Prevention as a nutrient powerhouse, providing 100% of the Daily Value for 17 key nutrients per 100 calories. Packed with antioxidants and nutrients like vitamin K, watercress offers a unique mix of nutrients and plant compounds. We suggests adding watercress to smoothies for a peppery kick or including it in salads for a unique flavor boost."
                ),
                Section(
                    subtitle = "Flaxseed",
                    text = "Ground flaxseed, or flax meal, is a superfood rich in fiber, which is crucial for gut health and regularity. It's also an excellent source of omega-3 fatty acids, especially for those who don't consume fish regularly. One tablespoon of flaxseed provides nearly 10% of the recommended dietary intake for fiber. Flaxseeds are also rich in healthy fats, protein, and polyphenols, compounds linked to lower cholesterol, blood pressure, and reduced risk of certain cancers and chronic diseases. Moreover, sprinkling flaxseed on cereal, adding it to smoothies, or using it as an egg substitute in baking."
                ),
                Section(
                    subtitle = "Eggs",
                    text = "Eggs are a nutrient powerhouse with high-quality protein and essential vitamins and minerals. And, yes—you can, and should, eat the yolk. Nearly half of an egg's protein and most of its vitamins and minerals, including those essential for brain and body health, are found in the yolk. The yolk contains choline, a nutrient that supports brain health, memory, mood, and more. Research also suggests that eggs may enhance the absorption of nutrients found in vegetables, such as vitamin E and carotenoids."
                ),
                Section(
                    subtitle = "Oats",
                    text = "Oats are a great source of fiber, making them heart-healthy and beneficial for people with pre-diabetes or type 2 diabetes. The type of fiber found in oats can help lower the risk of heart disease, stroke, and diabetes. Whole grain oats, in particular, can help reduce LDL cholesterol. The soluble fiber in oats, known as beta-glucan, instructs the liver to remove LDL cholesterol from the blood. Beta-glucan also binds to some of the cholesterol in the gut, preventing it from entering the bloodstream."
                ),
                Section(
                    subtitle = "Yogurt",
                    text = "Yogurt is an excellent source of protein, calcium, and probiotics. Probiotics are beneficial bacteria that live in the gut and can help improve digestion and immune function. A 2021 research found that consuming fermented dairy is associated with improved gastrointestinal, cardiovascular, and bone health, better weight maintenance, and a reduced risk of certain types of cancer."
                ),
                Section(
                    subtitle = "Kale",
                    text = "Kale is a plant-based source of calcium and is rich in vitamins C, E, K, B vitamins, and dietary carotenoids like lutein and zeaxanthin. Its nutrient density often earns it a spot among superfoods. Researchers praises kale for supporting brain health. Kale is a good source of folate, alpha-tocopherol, and other phytonutrients associated with lower rates of cognitive decline. A study of 960 older adults found that consuming about one serving of green leafy vegetables daily was linked to slower cognitive decline. A systematic review also found that higher intakes of green leafy vegetables, including kale, may promote higher levels of optimism and self-efficacy, and protect against depressive symptoms, Andrews adds."
                ),
                Section(
                    subtitle = "Beans",
                    text = "Beans are packed with plant-based protein, antioxidants, fiber, and are rich in folate, iron, and magnesium. The fiber in beans helps you stay full longer and manage blood sugar levels. Research suggests that antioxidants in black beans may lower the risk and slow the progression of chronic diseases. One specific flavonoid in black beans, anthocyanin, is associated with improved insulin resistance and inflammation levels."
                ),
                Section(
                    subtitle = "Chia Seeds",
                    text = "Chia seeds are a nutritional powerhouse, packed with omega-3 fatty acids, polyunsaturated fats, dietary fiber, protein, vitamins, and essential minerals. Additionally, these tiny seeds are a rich source of polyphenols and antioxidants, supporting overall health. Chia seeds also contain antioxidant compounds that may protect heart and brain health. The seeds have the ability to absorb liquid and form a gel-like consistency, which adds texture to various dishes. Mix them into yogurt, oatmeal, or create chia seed pudding for a nutritious and energy-boosting breakfast."
                ),
                Section(
                    subtitle = "Walnuts",
                    text = "Walnuts are packed with antioxidants, and research suggests walnut consumption may be associated with improved cognitive function. The polyphenols, tocopherols, and polyunsaturated fatty acids in walnuts contribute to their health benefits. Walnuts are also an excellent source of omega-3 alpha-linolenic acid (ALA), with 2.6 grams per ounce. A 2022 literature review found that given the accumulating evidence on dietary ALA and cardiovascular outcomes, food sources high in ALA, such as walnuts, should be included as part of a heart-healthy dietary pattern."
                ),
                Section(
                    subtitle = "Alaskan Sablefish",
                    text = "You may be familiar with the benefits of salmon, but Alaskan sablefish provides even more heart-healthy omega-3s. One 3-ounce serving offers 1,519 milligrams of DHA and EPA, two to nearly five times the amount found in salmon. Studies have shown that EPA and DHA are important for every stage of life, from brain health to heart health."
                ),
                Section(
                    subtitle = "Blueberries",
                    text = "In just over 80 calories, a 1-cup serving of blueberries delivers crave-worthy flavor and beneficial vitamins and minerals, including vitamin C, vitamin K, manganese, fiber, and phytonutrients. Recent research also links blueberries to gut health. A small 2023 study found that consuming about 1 ¼ cups of blueberries daily for six weeks relieved abdominal symptoms and improved well-being in adults with irritable bowel syndrome or functional dyspepsia. These benefits are attributed to the polyphenols in blueberries, which may have antioxidant, anti-inflammatory, and neuroprotective properties. The abundant antioxidants in blueberries may also protect brain cells, enhance cognitive performance, and improve blood flow for brain and cardiovascular health, adds Julie Pace, a functional dietitian nutritionist in Mississippi. She recommends adding a cup of these brain-boosting berries to smoothies, yogurt bowls, or salads."
                ),
                Section(
                    subtitle = "Quinoa",
                    text = "Quinoa stands out as a plant-based complete protein, meaning it contains all the essential amino acids. It is also rich in fiber, iron, and magnesium, supporting both heart health and digestion. A 2023 review found that quinoa may help manage blood sugar, exhibits antioxidant and anti-inflammatory activities, and supports healthy cholesterol levels."
                ),
                Section(
                    subtitle = "Sea Vegetables",
                    text = "Sea vegetables like kelp, nori, and wakame are nutrient-dense and sustainable foods widely used by many cultures. Kombu, a type of kelp, provides omega-3 fatty acids, calcium, iron, folate, magnesium, iodine, and other nutrients. A 3.5-ounce serving of kombu provides approximately 6.2 grams of fiber, more than twice the amount found in the same quantity of cabbage. Sea vegetables are also rich in compounds with potential antioxidant properties. We suggest making a flavorful seaweed salad by seasoning pre-cut kelp with minced garlic, salt, vinegar, soy sauce, and sesame oil. Ready-to-eat kelp can be found in the frozen section at some grocery stores."
                ),
                Section(
                    subtitle = "Cabbage",
                    text = "Cabbage is a nutrient-rich vegetable that deserves superfood status. Cabbage and other cruciferous vegetables contain sulforaphane, a sulfur-containing compound associated with heart health and a decreased risk of certain cancers. Red cabbage is also rich in anthocyanins, antioxidants linked to an increase in healthy gut bacteria and decreased inflammation."
                )
            )
        ),
        InformationPage(
            infoPageId = "3",
            imageId = R.drawable.ketosis,
            title = "Ketosis",
            openingQuestion = "Ketosis - What is it, it’s advantages and disadvantages",
            sections = listOf(
                Section(
                    subtitle = "What is Ketosis?",
                    text = "Ketosis occurs when the body lacks sufficient carbohydrates for energy and instead burns fat, producing ketones for fuel.\n" +
                            "Encountering the term \"ketosis\" often happens when researching diabetes or weight loss. Whether it's beneficial or harmful depends on various factors."
                ),
                Section(
                    subtitle = "Ketosis and the Keto Diet",
                    text = "\"Keto\" diets are popular weight loss regimes that induce ketosis. Besides promoting fat burning, ketosis can reduce hunger and preserve muscle mass.\n" +
                            "For healthy individuals without diabetes or pregnancy, ketosis typically starts after 3 or 4 days of consuming fewer than 50 grams of carbohydrates daily—roughly equivalent to three slices of bread, a cup of low-fat fruit yogurt, or two small bananas. Ketosis can also be initiated through fasting.\n" +
                            "A diet rich in fat and protein but low in carbs is termed a ketogenic or \"keto\" diet."
                ),
                Section(
                    subtitle = "Health Benefits of Ketosis",
                    text = "Beyond weight loss, ketosis may offer additional advantages. For instance, doctors may recommend ketogenic diets to children with epilepsy to help prevent seizures. Adults with epilepsy sometimes follow modified Atkins diets.\n" +
                            "Some studies suggest that ketogenic diets could potentially lower the risk of heart disease. Other research investigates their impact on conditions such as metabolic syndrome, insulin resistance, and type 2 diabetes. Additionally, ongoing research explores their effects on acne, cancer, polycystic ovary syndrome (PCOS), and neurodegenerative diseases like Alzheimer's, Parkinson's, and ALS."
                ),
                Section(
                    subtitle = "Symptoms and Side Effects of Ketosis",
                    text = "During the initial stages of a keto diet, you may experience what some refer to as \"keto flu,\" though it's not recognized as a medical condition. This period of discomfort might be attributed to withdrawal from sugars and carbs, changes in gut bacteria, or immune responses. Temporary side effects can include headache, fatigue, brain fog, irritability, constipation, sleep disturbances, nausea, stomachaches, dizziness, sugar cravings, cramps, muscle soreness, and halitosis, commonly known as ketosis breath."
                )
            )

        ),
        InformationPage(
            infoPageId = "4",
            imageId = R.drawable.eating_disorders,
            title = "Eating Disorders",
            openingQuestion = "Eating Disorders- examples and impacts",
            sections = listOf(
                Section(
                    subtitle = "What are Eating Disorders?",
                    text = "Eating disorders are complex mental health conditions characterized by unhealthy relationships with food, eating behaviors, and body image. They can affect people of any age, gender, or background, and often involve a combination of psychological, social, and biological factors."
                ),
                Section(
                    subtitle = "Anorexia Nervosa",
                    text = "One of the most well-known eating disorders is anorexia nervosa, where individuals severely restrict their food intake due to an intense fear of gaining weight or becoming fat. This disorder can lead to dangerously low body weight, nutritional deficiencies, and serious health complications."
                ),
                Section(
                    subtitle = "Bulimia Nervosa",
                    text = "Bulimia nervosa is another common eating disorder characterized by episodes of binge eating followed by purging behaviors, such as self-induced vomiting, excessive exercise, or the misuse of laxatives or diuretics. Individuals with bulimia often feel a lack of control during binge episodes and may experience shame or guilt afterward."
                ),
                Section(
                    subtitle = "Binge Eating Disorder",
                    text = "Binge eating disorder involves recurring episodes of consuming large amounts of food in a short period, accompanied by feelings of distress or lack of control. Unlike bulimia, individuals with binge eating disorder do not engage in purging behaviors, which can lead to obesity and related health problems."
                ),
                Section(
                    subtitle = "Other Specified Feeding or Eating Disorders (OSFED)",
                    text = "Other specified feeding or eating disorders (OSFED), formerly known as EDNOS (Eating Disorder Not Otherwise Specified), encompass a range of symptoms that do not fully meet the criteria for anorexia, bulimia, or binge eating disorder but still significantly impact a person's well-being and quality of life."
                ),
                Section(
                    subtitle = "Physical and Mental Health Consequences",
                    text = "Eating disorders can have severe physical consequences, including digestive problems, heart complications, electrolyte imbalances, and hormonal disturbances. They also take a toll on mental health, causing anxiety, depression, social isolation, and low self-esteem."
                ),
                Section(
                    subtitle = "Treatment and Support",
                    text = "Treatment for eating disorders typically involves a multidisciplinary approach, including medical monitoring, nutritional counseling, psychotherapy (such as cognitive-behavioral therapy or dialectical behavior therapy), and sometimes medication. Family involvement and support networks play crucial roles in recovery.\n" +
                            "Early detection and intervention are crucial for improving outcomes and preventing long-term health consequences. Educating oneself and others about eating disorders, promoting body positivity, and fostering healthy relationships with food are essential steps in creating a supportive environment for those affected by these challenging conditions."
                )
            )
        ),
        InformationPage(
            infoPageId = "5",
            imageId = R.drawable.meat_or_vegetarian,
            title = "Eating Meat or Being Vegetarian?",
            openingQuestion = "Eating meat or being vegetarian? Which one is healthier?",
            sections = listOf(
                Section(
                    subtitle = "Introduction",
                    text = "The debate over whether eating meat or adopting a vegetarian diet is healthier is multifaceted and often depends on various factors including individual health needs, nutritional balance, and ethical considerations."
                ),
                Section(
                    subtitle = "Benefits of Eating Meat",
                    text = "Eating meat provides a rich source of complete proteins, essential vitamins (such as B12 and iron), and minerals crucial for overall health and muscle function. Animal products are particularly dense in nutrients like heme iron, which is more readily absorbed by the body compared to non-heme iron found in plant sources. Additionally, meats like fish provide omega-3 fatty acids that support heart health and brain function. However, excessive consumption of red and processed meats has been associated with increased risks of cardiovascular disease and certain cancers."
                ),
                Section(
                    subtitle = "Benefits of a Vegetarian Diet",
                    text = "A well-planned vegetarian diet can offer numerous health benefits. Plant-based diets are typically high in fiber, antioxidants, and phytonutrients, which can help lower cholesterol, reduce inflammation, and support digestive health. Vegetarians often have lower risks of obesity, hypertension, type 2 diabetes, and some forms of cancer compared to omnivores. Moreover, vegetarian diets tend to be lower in saturated fats and cholesterol, promoting heart health."
                ),
                Section(
                    subtitle = "Nutritional Considerations",
                    text = "Vegetarian diets may require careful planning to ensure adequate intake of certain nutrients that are more abundant in animal products, such as vitamin B12, iron, zinc, and omega-3 fatty acids. Vegetarians may need to incorporate fortified foods or supplements to meet these needs effectively."
                ),
                Section(
                    subtitle = "Conclusion",
                    text = "Ultimately, the healthiness of eating meat versus being vegetarian depends on the quality and balance of the diet chosen. Both approaches can support good health and provide essential nutrients, but it's crucial to prioritize whole foods, variety, and moderation in either dietary choice. Factors such as personal health goals, ethical beliefs regarding animal welfare, and environmental sustainability also play significant roles in shaping individual dietary preferences and practices. Consulting with a healthcare professional or registered dietitian can provide personalized guidance to optimize nutritional intake and overall health based on individual needs and preferences."
                )
            )
        )
    )
}