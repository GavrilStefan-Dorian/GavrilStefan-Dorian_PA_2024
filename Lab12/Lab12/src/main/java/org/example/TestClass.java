    package org.example;

    @Test
    public class TestClass {
        public int ival;

        @Test
        public void testMethod() {
            System.out.println("Metoda de test a fost executată");
        }

        @Test
        public static void staticTestMethod() {
            System.out.println("Metoda statica de test a fost executată");
        }

        public void nonTestMethod() {
            System.out.println("Aceasta metodă nu este metodă de test");
        }

        @Test
        public void parameterTestMethod(int ival, String sval) {
            System.out.println("Metoda parametrizată a fost executată");
            System.out.println(ival + " " + sval);
        }

        @Test
        public static void parameterStaticTestMethod(int ival, String sval) {
            System.out.println("Metoda statica parametrizată a fost executată");
            System.out.println(ival + " " + sval);

        }
    }
    //D:\IdeaProjects\GavrilStefan-Dorian_PA_2024\Lab12\Lab12\bin\org\example\TestClass.class