package page.rightshift.tilegame;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import org.jetbrains.annotations.NotNull;

public abstract class TileEventHandler {
    private static int a;

    public static boolean fightEnemy(@NotNull Player player, @NotNull TiledMapTileLayer enemyLayer, @NotNull TiledMapTileLayer baseLayer) {
        TiledMapTileLayer.Cell cell = enemyLayer.getCell((int)player.pos.x, (int)player.pos.y);
        if (cell != null) {
            if(cell.getTile().getId() > 0) {
                // now we will fight the target

                int targetHP = 0;
                int targetWP = 0;
                int reward = 0;

                try {
                    targetHP = (int) cell.getTile().getProperties().get("hitpoints");
                    targetWP = (int) cell.getTile().getProperties().get("weaponPower");
                    reward = (int) cell.getTile().getProperties().get("reward");
                } catch (NullPointerException e) {
                    // probably just the placeholder tile left over from killing an enemy
                    // we can just skip over this
                    return false;
                }

                // get target details
                System.out.println("Target HP: " + targetHP + "    Target WP: " + targetWP);

                boolean playerTurn = false;

                // now start exchanging hits
                while (targetHP > 0 && player.hitpoints > 0) {
                    if(!playerTurn) {
                        player.hitpoints -= targetWP;
                        playerTurn = true;
                        System.out.println("You were hit for " + targetWP + ". Hitpoints are now " + player.hitpoints);
                    }

                    if(playerTurn) {
                        targetHP -= player.weapon.weaponPower;
                        playerTurn = false;
                        System.out.println("You hit the target for " + player.weapon.weaponPower + ". Hitpoints are now " + targetHP);
                    }
                }

                if(targetHP < 1) {
                    cell.setTile(baseLayer.getCell((int)player.pos.x, (int)player.pos.y).getTile());
                    player.gold += reward;
                }

                if(player.hitpoints < 1) {
                    player.living = false;

                }
                return true;
            }
        }
        return false;
    }

    public static boolean interactWithTile(@NotNull Player player,
                                           @NotNull TiledMapTileLayer functionalLayer) {
        TiledMapTileLayer.Cell cell = functionalLayer.getCell((int)player.pos.x, (int)player.pos.y);
        if (cell != null) {
            switch((int)cell.getTile().getProperties().get("functionalTileId")) {
                // right now I will just have a store that sells you a health potion that is 5 hitpoints
                // for 10 gold each
                // you will get gold for killing monsters, specified in its tile
                case 0:
                    if (player.gold >= 10) {
                        if(player.hitpoints + 5 <= 20) {
                            player.gold -= 10;
                            player.hitpoints += 5;
                        }
                    }
            }
        } else {
            DebugStuff.debug_print("Cell is null " + a);
            a++;
        }

        return false;
    }
}