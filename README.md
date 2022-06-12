# Chess
This project is Chess built through the GridWorldCaseStudy using Java.

It includes all official chess moves including en passant, promotion, castling, stalemates (3 repeating moves on both sides, if one side has no more moves it can make, and the king on one side repeating 50 moves). There's also checkmates, where one side's pieces corner the other side's king.

Out of all of these functions, checkmates was the hardest. This is because I have to restrict which piece can move during the time when the king has to be checkmated.  Then I have to store the location where the pieces can protect the king or kill the attackers, or location where the king can move. 

I had a tough time finding out where the King can move. Not only do I have to check the adjacent 8 spaces around the king, but also the spaces around the 8 spaces in order to see whether the king can take that piece or not to avoid getting checkmated.
