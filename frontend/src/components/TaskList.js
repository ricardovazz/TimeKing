import React, { useState, useEffect } from 'react';
import Card from '@material-tailwind/react/Card';
import CardBody from '@material-tailwind/react/CardBody';
import { Link } from "react-router-dom";
import {useParams} from 'react-router-dom';
import { AdjustmentsIcon } from '@heroicons/react/outline'
import { Button } from '@material-ui/core';
import { userService } from '../services/user.service';

 

  
export default function TaskList() {
    
    const  param  = useParams();
    var id = parseInt(param.projectID);
    //let data = getTaskByProjectID(id);
    //console.log(data.length)

    
    const [tasks, setTasks] = useState([]);
    useEffect(() => {
        userService.getProjectTasks(id).then(tasks => setTasks( tasks ));
    });

    return (
      <>
        <Card>
            <button
                  className="flex ml-3 py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-yellow-500 hover:bg-yellow-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                >
                  <Link to="/createtask"
                    state={{ id: id}}

                  >Create new Task</Link>                  
            </button>
            <CardBody>
                <div className="overflow-x-auto">
                    <table className="items-center w-full bg-transparent border-collapse">
                        <thead>
                            <tr>
                                <th className="px-2 text-blue-900 align-middle border-b border-solid border-gray-200 py-3 text-sm whitespace-nowrap text-left">
                                    Task Name
                                </th>
                                <th className="px-2 text-blue-900 align-middle border-b border-solid border-gray-200 py-3 text-sm whitespace-nowrap  text-left">
                                    Project Name
                                </th>
                                <th className="px-2 text-blue-900 align-middle border-b border-solid border-gray-200 py-3 text-sm whitespace-nowrap text-left">
                                    Status
                                </th>
                                <th className="px-2 text-blue-900 align-middle border-b border-solid border-gray-200 py-3 text-sm whitespace-nowrap text-left">
                                    Total Time
                                </th>
                                <th className="px-2 text-blue-900 align-middle border-b border-solid border-gray-200 py-3 text-sm whitespace-nowrap text-left">
                                    Options
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                           
                        {tasks.length>0 ? tasks.map(function(d, idx){
                            let link = "/editTask/" + (d.task_id).toString() ;
                            return (
                                <tr key={idx}>
                                <th className="border-b border-gray-200 align-middle font-light text-sm whitespace-nowrap px-2 py-4 text-left">
                                    {d.task_name}
                                </th>
                                <th className="border-b border-gray-200 align-middle font-light text-sm whitespace-nowrap px-2 py-4 text-left">
                                    {d.project_name}
                                </th>
                                <th className="border-b border-gray-200 align-middle font-light text-sm whitespace-nowrap px-2 py-4 text-left">
                                 
                                    {d.status}
                                </th>
                                <th className="border-b border-gray-200 align-middle font-light text-sm whitespace-nowrap px-2 py-4 text-left">
                                    {d.total_hours}
                                </th>
                                <th className="border-b border-gray-200 align-middle font-light text-sm whitespace-nowrap px-2 py-4 text-left">
                                {/* TODO this needs to link to edit task */}
                                <Link to={link}>
                                    <Button className="">
                                        <AdjustmentsIcon className="max-h-7 text-yellow-500 hover:bg-yellow-600"/>
                                    </Button>
                                </Link>
                                </th>
                            </tr>
                            )
                        }):
                         (<tr><th colSpan="5" className="font-mediumlight text-sm whitespace-nowrap px-2 py-4 ">No task found</th></tr>)
                        }
                        </tbody>
                    </table>
                </div>
            </CardBody>
        </Card>
      </>
    )
  }