/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfinding.eci2015;

import coppelia.FloatWA;
import coppelia.IntW;
import coppelia.remoteApi;

/**
 *
 * @author stein
 */
public class PathFindingECI2015 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        remoteApi vrep = new remoteApi();
//        # just in case, close all opened connections
//        vrep.simxFinish( -1 )
        vrep.simxFinish(-1);
//        PORT=19997
        int port = 19997;
//        clientID = vrep.simxStart("127.0.0.1", PORT, True, True, 5000, 5)
        int clientID = vrep.simxStart("127.0.0.1", port, true, true, 5000, 5);

//        # check if client connection successful
//        if clientID == -1:
//          print('Could not connect to remote API server')
//          sys.exit( 1 )
        if ( clientID == -1 ) {
            System.out.println("Could not connect to remote API server");
            System.exit(1);
        }

//        print('Connected to remote API server')
        System.out.println("Connected to remote API server");

//        ret, handle = vrep.simxGetObjectHandle( clientID, 'Quadricopter_target', vrep.simx_opmode_oneshot_wait )
        IntW handle = new IntW(-1);
        int ret = vrep.simxGetObjectHandle(clientID, "Quadricopter_target", handle, remoteApi.simx_opmode_oneshot_wait);

//        if ( not ret == vrep.simx_return_ok ):
//          print('Failed to retrieve handle for Quadricopter_target')
//          sys.exit( 1 )
        if ( ret != remoteApi.simx_return_ok ) {
            System.out.println("Failed to retrieve handle for Quadricopter_target");
            System.exit(1);
        }

        while ( true ) {
            FloatWA pos = new FloatWA(3);
//          ret, pos = vrep.simxGetObjectPosition( clientID, handle, -1, vrep.simx_opmode_oneshot_wait )
            ret = vrep.simxGetObjectPosition(clientID, handle.getValue(), -1, pos, remoteApi.simx_opmode_oneshot_wait);

//          if ( not ret == vrep.simx_return_ok ):
//            print('Failed to retrieve position for Quadricopter_target')
//            sys.exit( 1 )
            if ( ret != remoteApi.simx_return_ok ) {
                System.out.println("Failed to retrieve position for Quadricopter_target");
                System.exit(1);
            }

//          print pos
//          new_pos = pos
//          new_pos[0] = new_pos[0]+.01
            System.out.println(pos);
            FloatWA new_pos = new FloatWA(3);
            new_pos.getArray()[0] = pos.getArray()[0] + 0.01f;
            new_pos.getArray()[1] = pos.getArray()[1];
            new_pos.getArray()[2] = pos.getArray()[2];

            ret = vrep.simxSetObjectPosition(clientID, handle.getValue(), -1, new_pos, remoteApi.simx_opmode_oneshot_wait);
            if ( ret != remoteApi.simx_return_ok ) {
                System.out.println("Failed to set position for Quadricopter_target");
                System.exit(1);
            }

        }

    }

}
