package pieces;

public class Demo {
    
    public static void main(String[] args)
    {
        PiecePuzzle example = new PiecePuzzle(3,4, new Piece_C());
        PiecePuzzle example2 = new PiecePuzzle(3,4, new Piece_S());

        PiecePuzzle piege = new PiecePuzzle(3,4, new Piece_C());

        System.out.println(piege);
        
        System.out.println(example);
        System.out.println("--------------");
        example.tourner(false);
        System.out.println(example);
        System.out.println("--------------");
        example.tourner(false);
        System.out.println(example);
        System.out.println("--------------");

        System.out.println(example2);
        System.out.println("--------------");
        example2.tourner(true);
        System.out.println(example2);
        System.out.println("--------------");
        example2.tourner(true);
        System.out.println(example2);
    }
}

