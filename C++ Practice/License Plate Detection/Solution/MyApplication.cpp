#include "Utilities.h"
#include <iostream>
#include <fstream>
#include <list>
#include <experimental/filesystem> // C++-standard header file name
#include <filesystem> // Microsoft-specific implementation header file name
using namespace std::experimental::filesystem::v1;
using namespace std;

#define FOCAL_LENGTH_ESTIMATE 1770
#define PLATE_WIDTH_IN_MM 465
#define PLATE_HEIGHT_IN_MM 100
#define FRAMES_PER_SECOND 29.97
#define REQUIRED_DICE 0.8
const int LICENCE_PLATE_LOCATIONS[][5] = { {1, 67, 88, 26, 6}, {2, 67, 88, 26, 6}, {3, 68, 88, 26, 6},
	{4, 69, 88, 26, 6}, {5, 70, 89, 26, 6}, {6, 70, 89, 27, 6}, {7, 71, 89, 27, 6}, {8, 73, 89, 27, 6},
	{9, 73, 90, 27, 6}, {10, 74, 90, 27, 6}, {11, 75, 90, 27, 6}, {12, 76, 90, 27, 6}, {13, 77, 91, 27, 6},
	{14, 78, 91, 27, 6}, {15, 78, 91, 27, 6}, {16, 79, 91, 27, 6}, {17, 80, 92, 27, 6}, {18, 81, 92, 27, 6},
	{19, 81, 92, 28, 6}, {20, 82, 93, 28, 6}, {21, 83, 93, 28, 6}, {22, 83, 93, 28, 6}, {23, 84, 93, 28, 6},
	{24, 85, 94, 28, 6}, {25, 85, 94, 28, 6}, {26, 86, 94, 28, 6}, {27, 86, 94, 28, 6}, {28, 86, 95, 29, 6},
	{29, 87, 95, 29, 6}, {30, 87, 95, 29, 6}, {31, 88, 95, 29, 6}, {32, 88, 96, 29, 6}, {33, 89, 96, 29, 6},
	{34, 89, 96, 29, 6}, {35, 89, 97, 29, 6}, {36, 90, 97, 29, 6}, {37, 90, 97, 30, 6}, {38, 91, 98, 30, 6},
	{39, 91, 98, 30, 6}, {40, 92, 98, 30, 7}, {41, 92, 99, 30, 7}, {42, 93, 99, 30, 7}, {43, 93, 99, 30, 7},
	{44, 94, 100, 30, 7}, {45, 95, 100, 30, 7}, {46, 95, 101, 30, 7}, {47, 96, 101, 30, 7}, {48, 97, 102, 30, 7},
	{49, 97, 102, 31, 7}, {50, 98, 102, 31, 7}, {51, 99, 103, 31, 7}, {52, 99, 103, 32, 7}, {53, 100, 104, 32, 7},
	{54, 101, 104, 32, 7}, {55, 102, 105, 32, 7}, {56, 103, 105, 32, 7}, {57, 104, 106, 32, 7}, {58, 105, 106, 32, 7},
	{59, 106, 107, 32, 7}, {60, 107, 107, 32, 7}, {61, 108, 108, 32, 7}, {62, 109, 108, 33, 7}, {63, 110, 109, 33, 7},
	{64, 111, 109, 33, 7}, {65, 112, 110, 34, 7}, {66, 113, 111, 34, 7}, {67, 114, 111, 34, 7}, {68, 116, 112, 34, 7},
	{69, 117, 112, 34, 8}, {70, 118, 113, 35, 8}, {71, 119, 113, 35, 8}, {72, 121, 114, 35, 8}, {73, 122, 114, 35, 8},
	{74, 124, 115, 35, 8}, {75, 125, 116, 36, 8}, {76, 127, 116, 36, 8}, {77, 128, 117, 36, 8}, {78, 130, 118, 36, 8},
	{79, 132, 118, 36, 9}, {80, 133, 119, 37, 9}, {81, 135, 120, 37, 9}, {82, 137, 121, 37, 9}, {83, 138, 122, 38, 9},
	{84, 140, 122, 38, 9}, {85, 142, 123, 38, 9}, {86, 144, 124, 38, 9}, {87, 146, 125, 38, 9}, {88, 148, 126, 39, 9},
	{89, 150, 127, 39, 9}, {90, 152, 128, 39, 9}, {91, 154, 129, 40, 9}, {92, 156, 129, 40, 10}, {93, 158, 130, 40, 10},
	{94, 160, 131, 41, 10}, {95, 163, 133, 41, 10}, {96, 165, 133, 41, 10}, {97, 167, 135, 42, 10}, {98, 170, 135, 42, 10},
	{99, 172, 137, 43, 10}, {100, 175, 138, 43, 10}, {101, 178, 139, 43, 10}, {102, 180, 140, 44, 10}, {103, 183, 141, 44, 10},
	{104, 186, 142, 44, 11}, {105, 188, 143, 45, 11}, {106, 192, 145, 45, 11}, {107, 195, 146, 45, 11}, {108, 198, 147, 45, 11},
	{109, 201, 149, 46, 11}, {110, 204, 150, 47, 11}, {111, 207, 151, 47, 11}, {112, 211, 152, 47, 11}, {113, 214, 154, 48, 11},
	{114, 218, 155, 48, 12}, {115, 221, 157, 49, 12}, {116, 225, 158, 50, 12}, {117, 229, 160, 50, 12}, {118, 234, 162, 50, 12},
	{119, 237, 163, 51, 12}, {120, 241, 164, 52, 12}, {121, 245, 166, 52, 12}, {122, 250, 168, 52, 12}, {123, 254, 169, 53, 12},
	{124, 258, 171, 54, 12}, {125, 263, 173, 55, 12}, {126, 268, 175, 55, 12}, {127, 273, 177, 55, 12}, {128, 278, 179, 56, 13},
	{129, 283, 181, 57, 13}, {130, 288, 183, 57, 13}, {131, 294, 185, 58, 13}, {132, 299, 187, 59, 13}, {133, 305, 190, 59, 13},
	{134, 311, 192, 60, 13}, {135, 317, 194, 60, 14}, {136, 324, 196, 60, 14}, {137, 330, 198, 61, 14}, {138, 336, 201, 63, 14},
	{139, 342, 203, 64, 14}, {140, 349, 206, 65, 14}, {141, 357, 208, 65, 15}, {142, 364, 211, 66, 15}, {143, 372, 214, 67, 15},
	{144, 379, 217, 68, 15}, {145, 387, 220, 69, 15}, {146, 396, 223, 70, 15}, {147, 404, 226, 71, 16}, {148, 412, 229, 72, 16},
	{149, 422, 232, 73, 17}, {150, 432, 236, 74, 17}, {151, 440, 239, 75, 18}, {152, 450, 243, 76, 18}, {153, 460, 247, 77, 18},
	{154, 470, 250, 78, 19}, {155, 482, 254, 78, 19}, {156, 492, 259, 81, 19}, {157, 504, 263, 82, 20}, {158, 516, 268, 83, 20},
	{159, 528, 272, 85, 21}, {160, 542, 277, 85, 21}, {161, 554, 282, 88, 21}, {162, 569, 287, 88, 22}, {163, 584, 292, 89, 22},
	{164, 598, 297, 91, 23}, {165, 614, 302, 92, 24}, {166, 630, 308, 94, 24}, {167, 646, 314, 96, 25}, {168, 664, 320, 97, 26},
	{169, 681, 327, 100, 26}, {170, 700, 334, 101, 27}, {171, 719, 341, 103, 28}, {172, 740, 349, 105, 29}, {173, 762, 357, 107, 29},
	{174, 784, 365, 109, 30}, { 175, 808, 374, 110, 31 }, { 176, 832, 383, 113, 32 } };
const int NUMBER_OF_PLATES = sizeof(LICENCE_PLATE_LOCATIONS) / (sizeof(LICENCE_PLATE_LOCATIONS[0]));
const int FRAMES_FOR_DISTANCES[] = { 54,   70,   86,  101,  115,  129,  143,  158,  172 };
const int DISTANCES_TRAVELLED_IN_MM[] = { 2380, 2380, 2400, 2380, 2395, 2380, 2385, 2380 };
const double SPEEDS_IN_KMPH[] = { 16.0, 16.0, 17.3, 18.3, 18.5, 18.3, 17.2, 18.3 };

double compute_dice(Rect bounding_rect, Rect ground_truth) {
	//intersection location
	double left = max(bounding_rect.x, ground_truth.x);
	double right = min(bounding_rect.x + bounding_rect.width, ground_truth.x + ground_truth.width);
	double bottom = max(bounding_rect.y - bounding_rect.height, ground_truth.y - ground_truth.height);
	double top = min(bounding_rect.y, ground_truth.y);

	if (top > bottom && right > left) {
		//two times the intersection of the ground truth and the located object divided by the sum of the two areas
		double intersectionArea = (right - left) * (top - bottom);
		double combinedArea = bounding_rect.area() + ground_truth.area();
		double dice_result = (2 * intersectionArea) / combinedArea;
		return dice_result;
	}
	else return 0;
}
Mat blur_edges(Mat input) {
	Mat input_copy = input.clone();

	Rect leftRegion(0, 0, 50, 540);
	cv::GaussianBlur(input_copy(leftRegion), input_copy(leftRegion), Size(0, 0), 4);

	Rect topRegion(0, 0, 960, 50);
	cv::GaussianBlur(input_copy(topRegion), input_copy(topRegion), Size(0, 0), 4);

	return input_copy;
}
float getPosAngle(RotatedRect min_rect) {
	if (min_rect.size.height > min_rect.size.width) return min_rect.angle + 180;
	else return min_rect.angle + 90;
}
// Simple distance formula
float distance_form(float x1, float x2, float y1, float y2)
{
	return sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2));
}

void MyApplication()
{
	string video_filename("Media/CarSpeedTest1.mp4");
	VideoCapture video;
	video.open(video_filename);

	string filename("Media/LicencePlateTemplate.png");
	Mat template_image = imread(filename, -1);
	string background_filename("Media/CarSpeedTest1EmptyFrame.jpg");
	Mat static_background_image = imread(background_filename, -1);
	if ((!video.isOpened()) || (template_image.empty()) || (static_background_image.empty()))
	{
		if (!video.isOpened())
			cout << "Cannot open video file: " << video_filename << endl;
		if (template_image.empty())
			cout << "Cannot open image file: " << filename << endl;
		if (static_background_image.empty())
			cout << "Cannot open image file: " << background_filename << endl;
	}
	else
	{
		Mat gray_template_image;
		cvtColor(template_image, gray_template_image, COLOR_BGR2GRAY);

		Mat current_frame, previous_frame;
		video.set(cv::CAP_PROP_POS_FRAMES, 1);
		video >> current_frame;
		int frame_number = 1;
		double last_time = static_cast<double>(getTickCount());
		double frame_rate = video.get(cv::CAP_PROP_FPS);
		double time_between_frames = 1000.0 / frame_rate;
		double distance_to_cam = 0;
		int tp_count = 0; // num pos
		int fp_count = 0; // num false pos
		int tn_count = 0; // num true neg
		int fn_count = 0; // num false neg
		double distances_to_cam_result[9] = {};
		double distance_travelled_result[8] = {};
		Mat current_frame_safe = current_frame.clone();
		while (!current_frame.empty())
		{
			current_frame_safe = current_frame.clone();
			if (frame_number != 1) {

				Mat smoothed_image, grey_frame, grey_background, binary_image_otsu, diff_image, grey, dilated_image_test, eroded_image, smoothed_edges;
				cvtColor(static_background_image, grey_background, COLOR_BGR2GRAY);

				//Blur edges with noise
				smoothed_edges = blur_edges(current_frame);

				// Gaussian blur to reduce noise and convert to greyscale
				//resize(smoothed_edges, smoothed_image, Size(960, 540));
				GaussianBlur(smoothed_edges, smoothed_image, Size(41, 41), 1.5);
				//erosion followed by dilation (opening)
				erode(smoothed_image, eroded_image, Mat());
				dilate(eroded_image, dilated_image_test, Mat());
				//cv::imshow("eroded & dilated", dilated_image_test);

				cvtColor(dilated_image_test, grey_frame, COLOR_BGR2GRAY);

				// Draw license plate locations given on current frame
				Rect ground_truth = Rect(LICENCE_PLATE_LOCATIONS[frame_number - 1][1],
					LICENCE_PLATE_LOCATIONS[frame_number - 1][2],
					LICENCE_PLATE_LOCATIONS[frame_number - 1][3],
					LICENCE_PLATE_LOCATIONS[frame_number - 1][4]);
				//rectangle(current_frame, ground_truth, (255, 0, 0), 1);

				// Canny edge detection
				Mat edge_detected_image;
				Canny(grey_frame, edge_detected_image, 100, 200);
				//cv::imshow("edge detected", edge_detected_image);

				// Image Dilation
				Mat dilated_image;
				dilate(edge_detected_image, dilated_image, Mat());
				//cv::imshow("dilated", dilated_image);

				// Image Closing 
				Mat closed_image;
				morphologyEx(dilated_image, closed_image, MORPH_CLOSE, Mat());
				//cv::imshow("closed", closed_image);

				// Finding contours which match location and aspect ratio
				vector<vector<Point>> contours;
				vector<Vec4i> hierarchy;
				Mat closed_image_copy = closed_image.clone();
				findContours(closed_image_copy, contours, hierarchy, cv::RETR_TREE, cv::CHAIN_APPROX_NONE);
				Mat contours_image = Mat::zeros(closed_image_copy.size(), CV_8UC3);
				bool match_found = false;
				float highest_aspect, highest_angle;
				int highest_index;
				Rect highest_candidate;
				vector<vector<Point>> approx_contours(contours.size());
				for (int contour_number = 0; (contour_number < (int)contours.size()); contour_number++)
				{
					RotatedRect min_rect = minAreaRect(Mat(contours[contour_number]));
					double len = arcLength(Mat(contours[contour_number]), true);
					approxPolyDP(Mat(contours[contour_number]), approx_contours[contour_number], 0.02 * len, true);
					Rect bounding_rect = boundingRect(Mat(contours[contour_number]));
					// read points and angle
					cv::Point2f rect_points[4];
					min_rect.points(rect_points);
					float leng = distance_form(rect_points[0].x, rect_points[1].x, rect_points[0].y, rect_points[1].y);
					float wid = distance_form(rect_points[1].x, rect_points[2].x, rect_points[1].y, rect_points[2].y);
					float minArea = leng * wid;
					double area = contourArea(contours[contour_number]);
					float aspect_ratio = wid / leng;
					float positiveAngle = getPosAngle(min_rect);
					if (hierarchy[contour_number][2] < 0) {
						int z = 0;
					}
					if (frame_number < 177 && aspect_ratio < (4.65 + 0.6) && aspect_ratio >(4.65 - 0.6) && minArea < 1500 && area > 100 && area < 1500 && abs(minArea - area) < 300 && approx_contours[contour_number].size() >= 4 && approx_contours[contour_number].size() <= 10 && positiveAngle < 90 + 20 && positiveAngle > 90 - 20) {
						if (match_found == false)
						{
							highest_index = contour_number;
							highest_aspect = aspect_ratio;
							highest_angle = positiveAngle;
						}
						else {
							if (abs(4.65 - aspect_ratio) < abs(4.65 - highest_aspect)) { //Which has closer aspect ratio
								highest_index = contour_number;
								highest_aspect = aspect_ratio;
								highest_angle = positiveAngle;
							}
						}
						match_found = true;
					}
				}
				if (match_found == false) {
					if (frame_number < 177) {
						fn_count++;
					}
					else {
						tn_count++;
					}
				}
				else {
					Scalar colour(0xBDF7, 0xBDF7, 0xBDF7);
					drawContours(contours_image, contours, highest_index, colour, cv::FILLED, 8, hierarchy);
					match_found = true;
					Rect bounding_rect = boundingRect(Mat(contours[highest_index]));

					double dice_result = compute_dice(bounding_rect, ground_truth);
					if (dice_result > 0.8) {
						tp_count++;
						Point frame_no_location(200, 400);
						Scalar frame_no_colour(0, 0, 0xFF);
						rectangle(current_frame, Rect(bounding_rect.x, bounding_rect.y, bounding_rect.width, bounding_rect.height), (0, 0, 255), 1);
						//Distance = (Focal length x Width) / Pixels
						distance_to_cam = (PLATE_WIDTH_IN_MM * FOCAL_LENGTH_ESTIMATE) / bounding_rect.width;
						switch (frame_number)
						{
						case 54:
							distances_to_cam_result[0] = distance_to_cam;
							break;
						case 70:
							distance_travelled_result[0] = distances_to_cam_result[0] - distance_to_cam;
							distances_to_cam_result[1] = distance_to_cam;
							break;
						case 86:
							distance_travelled_result[1] = distances_to_cam_result[1] - distance_to_cam;
							distances_to_cam_result[2] = distance_to_cam;
							break;
						case 101:
							distance_travelled_result[2] = distances_to_cam_result[2] - distance_to_cam;
							distances_to_cam_result[3] = distance_to_cam;
							break;
						case 115:
							distance_travelled_result[3] = distances_to_cam_result[3] - distance_to_cam;
							distances_to_cam_result[4] = distance_to_cam;
							break;
						case 129:
							distance_travelled_result[4] = distances_to_cam_result[4] - distance_to_cam;
							distances_to_cam_result[5] = distance_to_cam;
							break;
						case 143:
							distance_travelled_result[5] = distances_to_cam_result[5] - distance_to_cam;
							distances_to_cam_result[6] = distance_to_cam;
							break;
						case 158:
							distance_travelled_result[6] = distances_to_cam_result[6] - distance_to_cam;
							distances_to_cam_result[7] = distance_to_cam;
							break;
						case 172:
							distance_travelled_result[7] = distances_to_cam_result[7] - distance_to_cam;
							distances_to_cam_result[8] = distance_to_cam;
							break;
						default:
							break;
						}
						putText(current_frame, to_string(distance_to_cam) + "mm to camera", Point(400, 15), FONT_HERSHEY_SIMPLEX, 0.4, frame_no_colour);
					}
					else {
						fp_count++;
						Point frame_no_location(200, 400);
						Scalar frame_no_colour(0, 0, 0xFF);
						rectangle(current_frame, Rect(bounding_rect.x, bounding_rect.y, bounding_rect.width, bounding_rect.height), (255, 0, 0), 1);

					}
				}
				//Display contoured image
				//cv::imshow("OTSU contours", contours_image);
			}
			previous_frame = current_frame_safe.clone();

			//Evaluation
			Point  recall_loc(210, 15);
			Point  prec_loc(210, 35);
			putText(current_frame, to_string(distance_to_cam) + "mm to camera", Point(400, 15), FONT_HERSHEY_SIMPLEX, 0.4, Scalar(0, 0, 0xFF));
			Scalar frame_no_colour_recall_prec(0, 0, 0xFF);
			double recall = ((double)tp_count) / ((double)(tp_count + fn_count));
			double precision = ((double)tp_count) / ((double)(tp_count + fp_count));
			putText(current_frame, "Recall: " + to_string(recall), recall_loc, FONT_HERSHEY_SIMPLEX, 0.4, frame_no_colour_recall_prec);
			putText(current_frame, "Precision: " + to_string(precision), prec_loc, FONT_HERSHEY_SIMPLEX, 0.4, frame_no_colour_recall_prec);

			Point tptn_loc(80, 15);
			Point fpfn_loc(80, 35);
			Scalar frame_no_colour(0, 0, 0xFF);
			putText(current_frame, "TP = " + to_string(tp_count) + " TN = " + to_string(tn_count), tptn_loc, FONT_HERSHEY_SIMPLEX, 0.4, frame_no_colour);
			putText(current_frame, "FP = " + to_string(fp_count) + " FN = " + to_string(fn_count), fpfn_loc, FONT_HERSHEY_SIMPLEX, 0.4, frame_no_colour);

			if (frame_number == 177) {
				printf("Precision: %f \n", precision);
				printf("Recall: %f \n", recall);
			}
			char frame_no[20];
			sprintf(frame_no, "%d", frame_number);
			Point frame_no_location(5, 15);
			putText(current_frame, frame_no, frame_no_location, FONT_HERSHEY_SIMPLEX, 0.4, frame_no_colour);

			//Display frame with detection and locations
			cv::imshow("Current Frame", current_frame);
			double current_time = static_cast<double>(getTickCount());
			double duration = (current_time - last_time) / getTickFrequency() / 1000.0;
			int delay = (time_between_frames > duration) ? ((int)(time_between_frames - duration)) : 1;
			last_time = current_time;
			char c = cv::waitKey(delay);
			for (int i = 0; i < 1; i++)
				video >> current_frame;
			frame_number++;
		}
		int speed_result[8] = {};
		//print results
		printf("Frame 54 -> 70: %f", distance_travelled_result[0]);//16 frames 
		printf("mm \n");
		printf("Ground truth distance difference: %f", fabs(DISTANCES_TRAVELLED_IN_MM[0] - distance_travelled_result[0]));
		printf("mm \n");
		double time_between_frames_seconds = (16 / FRAMES_PER_SECOND);
		double mm_per_sec = distance_travelled_result[0] / time_between_frames_seconds;
		double mm_per_hour = mm_per_sec * 60 * 60;
		double km_per_hour = mm_per_hour / 1000000;
		speed_result[0] = km_per_hour;
		printf("Vehicle Speed Estimate: %d", speed_result[0]);
		printf("km/h \n");
		printf("Ground truth speed difference: %f", fabs(SPEEDS_IN_KMPH[0] - speed_result[0]));
		printf("km/h \n");
		printf("\n");

		printf("Frame 70 -> 86: %f", distance_travelled_result[1]);//16 frames 
		printf("mm \n");
		printf("Ground truth distance difference: %f", fabs(DISTANCES_TRAVELLED_IN_MM[1] - distance_travelled_result[1]));
		printf("mm \n");
		mm_per_sec = distance_travelled_result[1] / time_between_frames_seconds;
		mm_per_hour = mm_per_sec * 60 * 60;
		km_per_hour = mm_per_hour / 1000000;
		speed_result[1] = km_per_hour;
		printf("Vehicle Speed Estimate: %d", speed_result[1]);
		printf("km/h \n");
		printf("Ground truth speed difference: %f", fabs(SPEEDS_IN_KMPH[1] - speed_result[1]));
		printf("km/h \n");
		printf("\n");

		printf("Frame 86 -> 101: %f", distance_travelled_result[2]);//15 frames 
		printf("mm \n");
		printf("Ground truth difference: %f", abs(DISTANCES_TRAVELLED_IN_MM[2] - distance_travelled_result[2]));
		printf("mm \n");
		time_between_frames_seconds = (15 / FRAMES_PER_SECOND);
		mm_per_sec = distance_travelled_result[2] / time_between_frames_seconds;
		mm_per_hour = mm_per_sec * 60 * 60;
		km_per_hour = mm_per_hour / 1000000;
		speed_result[2] = km_per_hour;
		printf("Vehicle Speed Estimate: %d", speed_result[2]);
		printf("km/h \n");
		printf("Ground truth speed difference: %f", fabs(SPEEDS_IN_KMPH[2] - speed_result[2]));
		printf("km/h \n");
		printf("\n");

		printf("Frame 101 -> 115: %f", distance_travelled_result[3]);//14 frames 
		printf("mm \n");
		printf("Ground truth difference: %f", abs(DISTANCES_TRAVELLED_IN_MM[3] - distance_travelled_result[3]));
		printf("mm \n");
		time_between_frames_seconds = (14 / FRAMES_PER_SECOND);
		mm_per_sec = distance_travelled_result[3] / time_between_frames_seconds;
		mm_per_hour = mm_per_sec * 60 * 60;
		km_per_hour = mm_per_hour / 1000000;
		speed_result[3] = km_per_hour;
		printf("Vehicle Speed Estimate: %d", speed_result[3]);
		printf("km/h \n");
		printf("Ground truth speed difference: %f", fabs(SPEEDS_IN_KMPH[3] - speed_result[3]));
		printf("km/h \n");
		printf("\n");

		printf("Frame 115 -> 129: %f", distance_travelled_result[4]);//14 frames 
		printf("mm \n");
		printf("Ground truth difference: %f", abs(DISTANCES_TRAVELLED_IN_MM[4] - distance_travelled_result[4]));
		printf("mm \n");
		mm_per_sec = distance_travelled_result[4] / time_between_frames_seconds;
		mm_per_hour = mm_per_sec * 60 * 60;
		km_per_hour = mm_per_hour / 1000000;
		speed_result[4] = km_per_hour;
		printf("Vehicle Speed Estimate: %d", speed_result[4]);
		printf("km/h \n");
		printf("Ground truth speed difference: %f", fabs(SPEEDS_IN_KMPH[4] - speed_result[4]));
		printf("km/h \n");
		printf("\n");

		printf("Frame 129 -> 143: %f \n", distance_travelled_result[5]);//14 frames 
		printf("Frame 143 -> 158: %f \n", distance_travelled_result[6]);//15 frames 
		printf("Frame 158 -> 172: %f \n", distance_travelled_result[7]);//14 frames 

		printf("Ground truth difference: %f \n", distance_travelled_result[1]);
		printf("Frame 86 -> 101: %f \n", distance_travelled_result[2]);
		printf("Frame 101 -> 115: %f \n", distance_travelled_result[3]);
		printf("Frame 115 -> 129: %f \n", distance_travelled_result[4]);

		cv::waitKey();
		cv::destroyAllWindows();
	}
}

