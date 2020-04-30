package cube;

//public enum Color {
//    WHITE,
//    BLUE,
//    RED,
//    GREEN,
//    ORANGE,
//    YELLOW;
//}

public enum CubeColor {
    WHITE {
        public String toString() {
            return "W";
        }
    },
    BLUE{
        public String toString() {
            return "B";
        }
    },
    RED{
        public String toString() {
            return "R";
        }
    },
    GREEN{
        public String toString() {
            return "G";
        }
    },
    ORANGE{
        public String toString() {
            return "O";
        }
    },
    YELLOW{
        public String toString() {
            return "Y";
        }
    };
}